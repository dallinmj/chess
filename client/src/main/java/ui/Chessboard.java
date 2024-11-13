package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class Chessboard {

    public static void main(String[] args){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        ChessPiece[][] board = {{
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK)},
                                {new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN)},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN)},
                                {new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT),
                                new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK)}
                                };
        out.print(ERASE_SCREEN);
        drawBoard(out, board, ChessGame.TeamColor.WHITE);
        out.print("\n");
        drawBoard(out, board, ChessGame.TeamColor.BLACK);
    }

    private static void drawBoard(PrintStream out, ChessPiece[][] board, ChessGame.TeamColor color) {
        drawRows(out, board, color);
    }

    private static void drawSquare(PrintStream out, ChessPiece piece){
        printPiece(out, piece);
    }

    private static void printPiece(PrintStream out, ChessPiece piece){
        if(piece == null){
            out.print("   ");
        } else {
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                setLightText(out);
            }else {
                setDarkText(out);
            }
                switch (piece.getPieceType()){
                    case KING -> out.print(" K ");
                    case QUEEN -> out.print(" Q ");
                    case BISHOP -> out.print(" B ");
                    case KNIGHT -> out.print(" N ");
                    case ROOK -> out.print(" R ");
                    case PAWN -> out.print(" P ");
                }
            }
        }

    private static void drawRows(PrintStream out, ChessPiece[][] board, ChessGame.TeamColor color) {
        boolean flip = (color == ChessGame.TeamColor.BLACK);

        drawBoarder(out, flip);
        if(!flip){
            for (int i = 0; i < 8; i++) {
                drawRow(out, board[i], i, 0);
            }
        } else {
            for (int i = 7; i >= 0; i--) {
                drawRow(out, board[i], i, 1);
            }
        }
        drawBoarder(out, flip);
    }

    private static void drawRow(PrintStream out, ChessPiece[] row, int odd, int flip) {
        drawEdge(out, odd);
        for (int i = 0; i < 8; i++) {
            int index = flip == 1 ? 7 - i : i;
            int evenOrOdd = i + odd ;
            if (evenOrOdd % 2 == 0 + flip) {
                setLightSquare(out);
            } else {
                setDarkSquare(out);
            }
            drawSquare(out, row[index]);
        }
        drawEdge(out, odd);
        out.print(RESET_BG_COLOR);
        out.print("\n");
    }

    private static void drawEdge(PrintStream out, int odd) {
        out.print(SET_TEXT_COLOR_BRONZE);
        out.print(SET_BG_COLOR_DARK_BROWN);
        out.print(" " + (8 - odd) + " ");
    }

    private static void drawBoarder(PrintStream out, boolean flip) {
        String string = flip ? " hgfedcba " : " abcdefgh ";
        out.print(SET_BG_COLOR_DARK_BROWN);
        out.print(SET_TEXT_COLOR_BRONZE);
        for (int i = 0; i < 10; i++) {
            out.print(" " + string.charAt(i) + " ");
        }
        out.print(RESET_BG_COLOR);
        out.print("\n");
    }

    private static void drawRow1(PrintStream out, ChessPiece[] row) {
        for (int i = 0; i < row.length; i++) {
            Runnable func;
            if (i % 2 == 0) {
                setLightSquare(out);
            } else {
                setDarkSquare(out);
            }
            drawSquare(out, row[i]);
        }
    }

    private static void setDarkSquare(PrintStream out){
        out.print(SET_BG_COLOR_BLACK);
    }
    private static void setDarkText(PrintStream out){
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }
    private static void setLightSquare(PrintStream out){
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }
    private static void setLightText(PrintStream out) {
        out.print(SET_TEXT_COLOR_WHITE);
    }
}
