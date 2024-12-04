package ui;

import chess.*;

import java.util.Arrays;
import java.util.Collection;

import model.GameData;
import network.ResponseException;
import network.ServerFacade;
import network.ServerMessageObserver;
import network.WebsocketCommunicator;
import requestresult.gamerequestresult.ListGamesRequest;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

public class ClientInGame implements Client{

    private final ChessBoard board;
    private final String auth;
    private final String color;
    private WebsocketCommunicator ws;
    private ServerMessageObserver serverMessageObserver;

    public ClientInGame(String serverURL, String gameId, String color, String auth) throws ResponseException {
        ServerFacade server = new ServerFacade(serverURL);
        this.serverMessageObserver = new ServerMessageObserver() {
            @Override
            public void notify(ServerMessage message) {
                System.out.println(message);
            }
        };
        ws = new WebsocketCommunicator(serverURL, serverMessageObserver);
        this.auth = auth;
        this.board = getGame(gameId);
        this.color = color;

        UserGameCommand userGameCommand = UserGameCommand.connect(auth, Integer.parseInt(gameId));

        ws.loadGame(gameId);
    }

    private ChessBoard getGame(String gameId) throws ResponseException {
        var result = ServerFacade.listGames(new ListGamesRequest(auth));
        System.out.println(result);
        var gamesList = result.games();
        for (var game : gamesList) {
            System.out.println(game);
            if (String.valueOf(game.gameID()).equals(gameId)) {
                ChessGame chessGame = game.getGame();
                return chessGame.getBoard();
            }
        }
        return null;
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "move" -> makeMove(params);
                case "resign" -> resign();
                case "highlight" -> highlight(params);
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @Override
    public void run() {
        System.out.println(help());
    }

    private String help() {
        return """
                Commands:
                help
                redraw
                leave
                move <from> <to>
                resign
                highlight <piece>""";
    }

    private String resign() {
        return "resign";
    }

    private String makeMove(String... params) {
        return "";
    }

    private String leave() {
        return "leave";
    }

    private String redraw() {
//        Chessboard.run
        return "redraw";
    }

    private String highlight(String... params) {
        if (params.length < 1) {
            return "highlight requires a piece to highlight";
        }
        ChessGame game = new ChessGame();
        game.setBoard(this.board);
        game.setTeamTurn(ChessGame.TeamColor.valueOf(this.color));
        Collection<ChessMove> moves = game.validMoves(new ChessPosition(params[0].charAt(0), params[0].charAt(1)));
        ChessPiece[][] grid = this.board.getSquares();
        System.out.println(moves);
        Chessboard.highlight(grid, game.getTeamTurn(), moves);

        return "highlight";
    }




}
