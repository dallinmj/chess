package chess;

import java.util.ArrayList;
import java.util.Collection;

/*
Calculate the potential moves for the Knight piece depending on its position and other pieces
 */
public class KnightCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        //Import needed info (Location, board, teamColor)
        Collection<ChessMove> validMoves = new ArrayList<>();
        int x = position.getColumn();
        int y = position.getRow();

        ChessMove move;
        ChessPosition originalPosition = new ChessPosition(y, x);

        // Up & Right
        if (x + 1 <= 8 && y + 2 <= 8) {
            move = new ChessMove(originalPosition, new ChessPosition(y + 2, x + 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + 2, x + 1), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Right & Up
        if (x + 2 <= 8 && y + 1 <= 8) {
            move = new ChessMove(originalPosition, new ChessPosition(y + 1, x + 2), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + 1, x + 2), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Right & Down
        if (x + 2 <= 8 && y - 1 > 0) {
            move = new ChessMove(originalPosition, new ChessPosition(y - 1, x + 2), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y - 1, x + 2), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Down & Right
        if (x + 1 <= 8 && y - 2 > 0) {
            move = new ChessMove(originalPosition, new ChessPosition(y - 2, x + 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y - 2, x + 1), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Down & Left
        if (x - 1 > 0 && y - 2 > 0) {
            move = new ChessMove(originalPosition, new ChessPosition(y - 2, x - 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y - 2, x - 1), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Left & Down
        if (x - 2 > 0 && y - 1 > 0) {
            move = new ChessMove(originalPosition, new ChessPosition(y - 1, x - 2), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y - 1, x - 2), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Left & Up
        if (x - 2 > 0 && y + 1 <= 8) {
            move = new ChessMove(originalPosition, new ChessPosition(y + 1, x - 2), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + 1, x - 2), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }

        // Up & Left
        if (x - 1 > 0 && y + 2 <= 8) {
            move = new ChessMove(originalPosition, new ChessPosition(y + 2, x - 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + 2, x - 1), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }
}
