package ui;

import chess.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

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
    private final Scanner scanner;

    public ClientInGame(String serverURL, String gameId, String color, String auth,
                        ServerMessageObserver serverMessageObserver) throws ResponseException {
        this.server = new ServerFacade(serverURL, serverMessageObserver);
        this.auth = auth;
        this.color = color;
        this.gameId = Integer.parseInt(gameId);
        server.connect(auth, this.gameId, this.color);
        this.scanner = new Scanner(System.in);
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
        System.out.print("\n>>> Are you sure you want to resign? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("yes") || input.equals("y")) {
            server.resign(auth, gameId, this.color);
        }
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

        ChessPiece.PieceType promotion = checkPromotion(from, to, game);

        ChessMove move = new ChessMove(from, to, promotion);
        server.makeMove(auth, gameId, move);
        return "";
    }

    private ChessPiece.PieceType checkPromotion(ChessPosition from, ChessPosition to, ChessGame game) throws ResponseException {
        ChessPiece piece = game.getBoard().getPiece(from);

        // Ensure the piece is a pawn
        if (piece == null || piece.getPieceType() != ChessPiece.PieceType.PAWN) {
            return null;
        }

        // Check if the pawn has reached the last rank
        boolean isWhitePromotion = piece.getTeamColor() == ChessGame.TeamColor.WHITE && to.getRow() == 8;
        boolean isBlackPromotion = piece.getTeamColor() == ChessGame.TeamColor.BLACK && to.getRow() == 1;

        if (isWhitePromotion || isBlackPromotion) {
            ChessPiece.PieceType promotion = null;

            while (promotion == null) {
                System.out.print("\n>>> Select a piece to promote to (queen, rook, bishop, knight): ");
                String input = scanner.nextLine().trim().toLowerCase();

                switch (input) {
                    case "queen", "q" -> promotion = ChessPiece.PieceType.QUEEN;
                    case "rook", "r" -> promotion = ChessPiece.PieceType.ROOK;
                    case "bishop", "b" -> promotion = ChessPiece.PieceType.BISHOP;
                    case "knight", "n" -> promotion = ChessPiece.PieceType.KNIGHT;
                    default -> System.out.println("Invalid input. Please enter queen, rook, bishop, or knight.");
                }
            }

            return promotion;
        }

        return null;
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
