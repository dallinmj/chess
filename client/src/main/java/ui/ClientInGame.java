package ui;

import chess.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import model.GameData;
import network.ResponseException;
import network.ServerFacade;
import network.ServerMessageObserver;

public class ClientInGame implements Client{

    private final ServerFacade server;
    private final String auth;
    private final String color;
    private final int gameId;
    private ChessBoard board;
    private GameData gameData;

    public ClientInGame(String serverURL, String gameId, String color, String auth,
                        ServerMessageObserver serverMessageObserver) throws ResponseException {
        this.server = new ServerFacade(serverURL, serverMessageObserver);
        this.auth = auth;
        this.color = color;
        this.gameId = Integer.parseInt(gameId);
        server.connect(auth, this.gameId, this.color);
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redraw(this.gameData);
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

    private String resign() throws ResponseException {

        server.resign(auth, gameId, this.color);
        return "";
    }

    public String makeMove(String... params) throws ResponseException {

        String invalid = "\n>>> Error: Invalid move\n";

        ChessGame game = this.gameData.getGame();
        if (!Objects.equals(this.color, game.getTeamTurn().toString().toLowerCase())) {
            return invalid;
        }

        if (params.length != 2) {
            return invalid;
        }

        if (params[0].length() != 2 || params[1].length() != 2) {
            return invalid;
        }

        ChessPosition from = parsePosition(params[0]);
        ChessPosition to = parsePosition(params[1]);

        if (from.equals(to)) {
            return invalid;
        }

        if (to.getColumn() < 1 || to.getColumn() > 8 || from.getColumn() < 1 || from.getColumn() > 8
                || from.getRow() < 1 || to.getRow() > 8 || from.getRow() > 8 || to.getRow() < 1) {
            return invalid;
        }

        ChessMove move = new ChessMove(from, to, null); // Fix promotion
        server.makeMove(auth, gameId, move);
        return "";
    }

    private ChessPosition parsePosition(String position) {
        int col = position.charAt(0) - 'a' + 1;
        int row = position.charAt(1) - '1' + 1;
        return new ChessPosition(row, col);
    }

    private String leave() throws ResponseException {
        server.leave(auth, gameId, this.color);
        return "leave:" + auth;
    }

    public String redraw(GameData gameData) {
        ChessGame game = gameData.getGame();
        ChessBoard board = game.getBoard();

        this.gameData = gameData;
        this.board = board;

        if (Objects.equals(this.color, "null")) {
            Chessboard.run(board.getSquares(), ChessGame.TeamColor.WHITE);
            return "";
        }

        Chessboard.run(board.getSquares(), ChessGame.TeamColor.valueOf(this.color.toUpperCase()));
        return "";
    }

    private String highlight(String... params) {
        if (params.length < 1) {
            return "\n>>>highlight requires a piece to highlight\n";
        }

        ChessGame game = this.gameData.getGame();
        ChessPosition position = parsePosition(params[0]);

        if (position.getColumn() < 1 || position.getColumn() > 8 || position.getRow() < 1 || position.getRow() > 8) {
            return "\n>>>highlight requires a valid position\n";
        }

        Collection<ChessMove> moves = game.validMoves(position);
        ChessPiece[][] grid = this.board.getSquares();

        if (Objects.equals(color, "null")) {
            Chessboard.highlight(grid, ChessGame.TeamColor.WHITE, moves);
            return "";
        }

        Chessboard.highlight(grid, ChessGame.TeamColor.valueOf(this.color.toUpperCase()), moves);
        return "";
    }
}
