package service;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import model.GameData;
import requestresult.gamerequestresult.*;

import java.util.Random;

public class GameService {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws DataAccessException {
        authDAO.getAuth(listGamesRequest.authToken());
        return new ListGamesResult(gameDAO.listGames());
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        authDAO.getAuth(createGameRequest.authToken());
        int gameID = new Random().nextInt(1000);
        gameDAO.createGame(new GameData(gameID, null, null,
                createGameRequest.gameName(), new ChessGame()));
        return new CreateGameResult(gameID);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws DataAccessException {
        authDAO.getAuth(joinGameRequest.authToken());
        String username = authDAO.getUsername(joinGameRequest.authToken());
        GameData gameData = gameDAO.getGame(joinGameRequest.gameID());
        String player = gameDAO.checkTeamColor(gameData, joinGameRequest.color());
        if (player == null) {
            gameDAO.addPlayer(gameData, joinGameRequest.color(), username);
            return new JoinGameResult();
        }
        throw new DataAccessException("Error: already taken");
    }

    public void leaveGame(String authToken, int gameID, String color) throws DataAccessException {
        authDAO.getAuth(authToken);
        GameData gameData = gameDAO.getGame(gameID);
        gameDAO.removePlayer(gameData, color);
    }

    public void makeMove(int gameId, ChessMove move) throws DataAccessException {
        GameData gameData = gameDAO.getGame(gameId);
        ChessGame game = gameData.game();
        try {
            game.makeMove(move);
            gameDAO.updateGame(gameData, game);
        } catch (Exception e) {
            if (e.getMessage() == null) {
                throw new DataAccessException("Invalid move");
            }
            throw new DataAccessException(e.getMessage());
        }
    }

    public void endGame(String authToken, int gameID) throws DataAccessException {
        authDAO.getAuth(authToken);
        GameData gameData = gameDAO.getGame(gameID);
        ChessGame game = gameData.game();
        game.end();
        gameDAO.updateGame(gameData, game);
    }

    public LoadGameResult loadGame(LoadGameRequest loadGameRequest) throws DataAccessException {
        authDAO.getAuth(loadGameRequest.authy());
        GameData gameData = gameDAO.getGame(loadGameRequest.gameId());
        return new LoadGameResult(gameData);
    }

    public void notValidID(Integer gameID) throws DataAccessException {
        gameDAO.getGame(gameID);
    }

    public void notValidMove(int gameID, ChessMove move, String auth) throws DataAccessException, InvalidMoveException {
        GameData gameData = gameDAO.getGame(gameID);
        ChessGame game = gameData.game();
        String username = authDAO.getUsername(auth);

        String playerColor = getPlayerColor(gameData, username);

        game.end();
        game.isCorrectPlayer(move, playerColor);
    }

    public String getPlayerColor(GameData gameData, String username) throws DataAccessException {
        String whiteUsername = gameDAO.checkTeamColor(gameData, "white");
        String blackUsername = gameDAO.checkTeamColor(gameData, "black");
        if (username.equals(whiteUsername)) {
            return "white";
        } else if (username.equals(blackUsername)) {
            return "black";
        }
        throw new DataAccessException("Error: not a player");
    }

    public boolean isInCheck(GameData updatedGameData) {
        ChessGame game = updatedGameData.game();
        return game.eitherTeamInCheck();
    }

    public boolean isInCheckmate(GameData updatedGameData) {
        ChessGame game = updatedGameData.game();
        return game.isInCheckmate(ChessGame.TeamColor.WHITE) || game.isInCheckmate(ChessGame.TeamColor.BLACK);
    }

    public boolean isInStalemate(GameData updatedGameData) {
        ChessGame game = updatedGameData.game();
        return game.isInStalemate(ChessGame.TeamColor.WHITE) || game.isInStalemate(ChessGame.TeamColor.BLACK);
    }

    public boolean isOver(GameData gameData) {
        ChessGame game = gameData.game();
        return game.isOver();
    }
}
