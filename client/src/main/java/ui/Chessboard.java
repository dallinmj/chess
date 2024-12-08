package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class Chessboard {

    public static void highlight(ChessPiece[][] board, ChessGame.TeamColor color, Collection<ChessMove> highlights) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        drawBoard(out, board, color, highlights);
    }

    public static void run(ChessPiece[][] board, ChessGame.TeamColor color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        drawBoard(out, board, color, null);
    }

    private static void drawBoard(PrintStream out, ChessPiece[][] board, ChessGame.TeamColor color, Collection<ChessMove> highlights) {
        drawRows(out, board, color, highlights);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawSquare(PrintStream out, ChessPiece piece, boolean isHighlighted, boolean isLight) {
        if (isHighlighted) {
            setHighlightSquare(out, isLight);
        }
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

    private static void drawRows(PrintStream out, ChessPiece[][] board, ChessGame.TeamColor color, Collection<ChessMove> highlights) {
        boolean flip = (color == ChessGame.TeamColor.BLACK);

        drawBoarder(out, flip);

        // If black is at bottom (flip = true), print from top to bottom (i=0 to 7)
        // and flip columns (pass flip=1 for drawRow).
        if (flip) {
            for (int i = 0; i < 8; i++) {
                drawRow(out, board[i], i, 0, highlights); // 1 means flip columns
            }
        } else {
            // White at bottom: print from bottom to top (7 down to 0), no column flip (0)
            for (int i = 7; i >= 0; i--) {
                drawRow(out, board[i], i, 1, highlights);
            }
        }
        drawBoarder(out, flip);
    }

    private static void drawRow(PrintStream out, ChessPiece[] row, int rowIndex, int flip, Collection<ChessMove> highlights) {
        drawEdge(out, rowIndex);
        for (int col = 0; col < 8; col++) {
            int index = flip == 0 ? 7 - col : col;
            int evenOrOdd = col + rowIndex;

            boolean isHighlighted = isSquareHighlighted(rowIndex, index, highlights);

            boolean isLight = false;
            if (evenOrOdd % 2 == 0 + flip) {
                setLightSquare(out);
                isLight = true;
            } else {
                setDarkSquare(out);
            }

            drawSquare(out, row[index], isHighlighted, isLight);
        }
        drawEdge(out, rowIndex);
        out.print(RESET_BG_COLOR);
        out.print("\n");
    }

    private static boolean isSquareHighlighted(int row, int col, Collection<ChessMove> highlights) {
        if (highlights == null) {
            return false;
        }
        for (ChessMove move : highlights) {
            if (move.getEndPosition().getRow() - 1 == row && move.getEndPosition().getColumn() - 1 == col) {
                return true;
            }
        }
        return false;
    }

    private static void drawEdge(PrintStream out, int odd) {
        out.print(SET_TEXT_COLOR_BRONZE);
        out.print(SET_BG_COLOR_DARK_BROWN);
        out.print(" " + (1 + odd) + " ");
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

    private static void setHighlightSquare(PrintStream out, boolean isLight) {
        if (isLight) {
            out.print(SET_BG_COLOR_YELLOW);
        } else {
            out.print(SET_BG_COLOR_DARK_YELLOW);
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
