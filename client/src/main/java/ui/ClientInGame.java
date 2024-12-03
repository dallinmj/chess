package ui;

import chess.*;

import java.util.Arrays;
import java.util.Collection;

import model.GameData;
import network.ResponseException;
import network.ServerFacade;
import requestresult.gamerequestresult.ListGamesRequest;

public class ClientInGame implements Client{

    private final ChessBoard board;
    private final String auth;
    private final String color;

    public ClientInGame(String serverURL, String gameId, String auth, String color) throws ResponseException {
        ServerFacade server = new ServerFacade(serverURL);
        this.auth = auth;
        this.board = getGame(gameId);
        this.color = color;
    }

    private ChessBoard getGame(String gameId) throws ResponseException {
        var result = ServerFacade.listGames(new ListGamesRequest(auth));
        var gamesList = result.games();
        for (var game : gamesList) {
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
                case "move" -> move(params);
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

    private String highlight(String... params) {
        if (params.length < 1) {
            return "highlight requires a piece to highlight";
        }
        ChessGame game = new ChessGame();
        game.setBoard(this.board);
        game.setTeamTurn(ChessGame.TeamColor.valueOf(this.color));
        Collection<ChessMove> moves = game.validMoves(new ChessPosition(params[0].charAt(0), params[0].charAt(1)));
        ChessPiece[][] grid = null;
        String highlights = this.board.
        Chessboard.highlight(grid, this.color, )

        return "highlight";
    }




}
