package server.Websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import requestresult.gamerequestresult.LoadGameRequest;
import service.GameService;
import service.UserService;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebsocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final GameService gameService;
    private final UserService userService;

    public WebsocketHandler(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

        if (isNotValid(command)){
            var error = new ErrorMessage("Invalid command");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        switch (command.getCommandType()) {
            case CONNECT -> connect(command.getAuthToken(), command.getGameID(), command.getColor(), session);
            case MAKE_MOVE -> {
                MakeMoveCommand moveCommand = new Gson().fromJson(message, MakeMoveCommand.class);
                makeMove(moveCommand.getAuthToken(), moveCommand.getGameID(), moveCommand.getMove(), session);
            }
            case LEAVE -> leave(command.getAuthToken(), command.getGameID(), command.getColor());
            case RESIGN -> resign(command.getAuthToken(), command.getGameID(), command.getColor(), session);
            }
        }

    private boolean isNotValid(UserGameCommand command) {
        try {
        gameService.notValidID(command.getGameID());
        userService.notValidAuth(command.getAuthToken());
        } catch (Exception ignored) {
            return true;
        }
        return false;
    }

    private void connect(String auth, int gameId, String color, Session session) throws IOException, DataAccessException {
        connections.add(auth, gameId, session);

        var LoadGameResult = gameService.loadGame(new LoadGameRequest(auth, gameId));
        var gameData = LoadGameResult.gameData();
        var loadGameMessage = new LoadGameMessage(gameData);
        session.getRemote().sendString(new Gson().toJson(loadGameMessage));

        String username = userService.getUsernameFromAuth(auth);
        String message;
        if (isObserver(color, username)) {
            message = String.format("%s is observing the game!", username);
        } else {
            message = String.format("%s joined the game as %s!", username, color);
        }
        var notification = new NotificationMessage(message);
        connections.broadcast(auth, gameId, notification);
    }

    private void makeMove(String auth, int gameID, ChessMove move, Session session) throws IOException, DataAccessException {
        int fromRow = (move.getStartPosition().getRow());
        int fromColumn = (move.getStartPosition().getColumn());
        int toRow = (move.getEndPosition().getRow());
        int toColumn = (move.getEndPosition().getColumn());

        if (isNotValid(auth, gameID, move)) {
            var error = new ErrorMessage("Invalid move");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        String from = convertToChessNotation(fromRow, fromColumn);
        String to = convertToChessNotation(toRow, toColumn);

        String username = userService.getUsernameFromAuth(auth);
        var message = String.format("%s made a move: %s to %s", username, from, to);
        var notification = new NotificationMessage(message);

        try {
            gameService.makeMove(gameID, move);

            GameData updatedGameData = gameService.loadGame(new LoadGameRequest(auth, gameID)).gameData();
            var updatedLoadGameMessage = new LoadGameMessage(updatedGameData);
            session.getRemote().sendString(new Gson().toJson(updatedLoadGameMessage));

            connections.broadcast(auth, gameID, updatedLoadGameMessage);
            connections.broadcast(auth, gameID, notification);

            if (gameService.isInCheckmate(updatedGameData)) {
                var checkmateMessage = new NotificationMessage("Player in checkmate! Game over!");
                connections.broadcast(null, gameID, checkmateMessage);
            } else if (gameService.isInCheck(updatedGameData)) {
                var checkMessage = new NotificationMessage("Player in check!");
                connections.broadcast(null, gameID, checkMessage);
            } else if (gameService.isInStalemate(updatedGameData)) {
                var stalemateMessage = new NotificationMessage("Game in stalemate! Game over!");
                connections.broadcast(null, gameID, stalemateMessage);
            }

        } catch (DataAccessException e) {
            var error = new ErrorMessage(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(error));
        }
    }

    private boolean isNotValid(String auth, int gameID, ChessMove move) {
        try {
            gameService.notValidID(gameID);
            userService.notValidAuth(auth);
            gameService.notValidMove(gameID, move, auth);
        } catch (Exception ignored) {
            return true;
        }
        return false;
    }

    public String convertToChessNotation(int row, int column) {
        char columnLetter = (char) ('a' + (column - 1));
        return columnLetter + String.valueOf(row);
    }

    private void leave(String auth, int gameID, String color) throws IOException, DataAccessException {
        connections.remove(auth);
        String username = userService.getUsernameFromAuth(auth);
        var message = String.format("%s left the game :(", username);
        var notification = new NotificationMessage(message);
        connections.broadcast(auth, gameID, notification);
        gameService.leaveGame(auth, gameID, color);
    }

    private void resign(String auth, int gameID, String color, Session session) throws IOException, DataAccessException {
        String username = userService.getUsernameFromAuth(auth);

        if (isObserver(color, username)) {
            var error = new ErrorMessage("You are an observer");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        GameData gameData = gameService.loadGame(new LoadGameRequest(auth, gameID)).gameData();
        if (gameService.isOver(gameData)) {
            var error = new ErrorMessage("Game is already over");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        var message = String.format("%s resigned!", username);
        var notification = new NotificationMessage(message);
        connections.broadcast(null, gameID, notification);
        gameService.endGame(auth, gameID);
    }

    private boolean isObserver(String color, String username) {
        return Objects.equals(color, "null") || Objects.equals(username, "observer");
    }
}