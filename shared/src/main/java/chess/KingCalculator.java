package chess;

import java.util.ArrayList;
import java.util.Collection;

/*
Calculate the potential moves for the Bishop piece depending on its position and other pieces
 */
public class KingCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        //Import needed info (Location, board, teamColor)
        Collection<ChessMove> validMoves = new ArrayList<>();
        int x = position.getColumn();
        int y = position.getRow();

        ChessMove move;
        ChessPosition originalPosition = new ChessPosition(y, x);

        // Up & Right
        if (x + 1 < 8 && y + 1 < 8) {
            move = new ChessMove(originalPosition, new ChessPosition(y + 1, x + 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + 1, x + 1), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Down & Right
        if (x + 1 < 8 && y - 1 > 0) {
            move = new ChessMove(originalPosition, new ChessPosition(y - 1, x + 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y - 1, x + 1), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Down & Left
            if (x - 1 > 0 && y - 1 > 0) {
                move = new ChessMove(originalPosition, new ChessPosition(y - 1, x - 1), null);
                String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y - 1, x - 1), teamColor);
                if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                    validMoves.add(move);
                }
            }

        // Up & Left
        if (x - 1 > 0 && y + 1 < 8) {
            move = new ChessMove(originalPosition, new ChessPosition(y + 1, x - 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + 1, x - 1), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Up
        if (y + 1 < 8) {
            move = new ChessMove(originalPosition, new ChessPosition(y + 1, x), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + 1, x), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Down
        if (y - 1 > 0) {
            move = new ChessMove(originalPosition, new ChessPosition(y - 1, x), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y - 1, x), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Right
        if (x + 1 < 8) {
            move = new ChessMove(originalPosition, new ChessPosition(y, x + 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y, x + 1), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Left
        if (x - 1 > 0) {
            move = new ChessMove(originalPosition, new ChessPosition(y, x - 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y, x - 1), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }
}
