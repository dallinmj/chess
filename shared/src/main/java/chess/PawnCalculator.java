package chess;

import java.util.ArrayList;
import java.util.Collection;

/*
Calculate the potential moves for the Bishop piece depending on its position and other pieces
 */
public class PawnCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        //Import needed info (Location, board, teamColor)
        Collection<ChessMove> validMoves = new ArrayList<>();
        int x = position.getColumn();
        int y = position.getRow();

        ChessMove move;
        ChessPosition originalPosition = new ChessPosition(y, x);
        boolean promotion = false;

        // Forward
        if ((y + 1 <= 8 && teamColor == ChessGame.TeamColor.WHITE) || (y - 1 > 0 && teamColor == ChessGame.TeamColor.BLACK)) {
            int upOrDown = teamColor == ChessGame.TeamColor.WHITE ? 1 : -1;
            move = new ChessMove(originalPosition, new ChessPosition(y + upOrDown, x), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + upOrDown, x), teamColor);
            if (checkPiece.equals("empty") || checkPiece.equals("enemy")) {
                if (y + upOrDown == 8 || y + upOrDown == 1){
                    for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                        if (type == ChessPiece.PieceType.PAWN || type == ChessPiece.PieceType.KING){
                            continue;
                        }
                        move = new ChessMove(originalPosition, new ChessPosition(y + upOrDown, x), type);
                        validMoves.add(move); // Promotion
                    }
                } else {
                    validMoves.add(move);
                }
            }
        }
        // Double Forward
        if ((y == 2 && teamColor == ChessGame.TeamColor.WHITE) || (y == 7 && teamColor == ChessGame.TeamColor.BLACK)){
            int upOrDown = teamColor == ChessGame.TeamColor.WHITE ? 2 : -2;
            move = new ChessMove(originalPosition, new ChessPosition(y + upOrDown, x), null);
            String checkPieceClose = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + (upOrDown / 2), x), teamColor);
            String checkPieceFar = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + upOrDown, x), teamColor);
            if (checkPieceClose.equals("empty") && (checkPieceFar.equals("empty") || checkPieceFar.equals("enemy"))) {
                validMoves.add(move);
            }
        }

        // Diagonal Left
        if ((y + 1 <= 8 && x - 1 > 0 && teamColor == ChessGame.TeamColor.WHITE) || (y - 1 > 0 && x - 1 > 0 && teamColor == ChessGame.TeamColor.BLACK)) {
            int upOrDown = teamColor == ChessGame.TeamColor.WHITE ? 1 : -1;
            move = new ChessMove(originalPosition, new ChessPosition(y + upOrDown, x - 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + upOrDown, x - 1), teamColor);
            if (checkPiece.equals("enemy")) {
                if (y + upOrDown == 8 || y + upOrDown == 1) {
                    for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                        if (type == ChessPiece.PieceType.PAWN || type == ChessPiece.PieceType.KING){ {
                            continue;
                        }
                    }
                        move = new ChessMove(originalPosition, new ChessPosition(y + upOrDown, x - 1), type);
                        validMoves.add(move); // Promotion
                    }
                } else {
                    validMoves.add(move);
                }
            }
        }

        // Diagonal Right
        if ((y + 1 <= 8 && x + 1 <= 8 && teamColor == ChessGame.TeamColor.WHITE) || (y - 1 > 0 && x + 1 <= 8 && teamColor == ChessGame.TeamColor.BLACK)) {
            int upOrDown = teamColor == ChessGame.TeamColor.WHITE ? 1 : -1;
            move = new ChessMove(originalPosition, new ChessPosition(y + upOrDown, x + 1), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y + upOrDown, x + 1), teamColor);
            if (checkPiece.equals("enemy")) {
                if (y + upOrDown == 8 || y + upOrDown == 1) {
                    for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                        if (type == ChessPiece.PieceType.PAWN || type == ChessPiece.PieceType.KING){ {
                            continue;
                        }
                    }
                        move = new ChessMove(originalPosition, new ChessPosition(y + upOrDown, x + 1), type);
                        validMoves.add(move); // Promotion
                    }
                } else {
                    validMoves.add(move);
                }
            }
        }
        return validMoves;
    }
}
