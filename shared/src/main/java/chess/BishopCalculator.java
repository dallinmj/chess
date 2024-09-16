package chess;

import java.util.ArrayList;
import java.util.Collection;

/*
Calculate the potential moves for the Bishop piece depending on its position and other pieces
 */
public class BishopCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        //Import needed info (Location, board, teamColor)
        Collection<ChessMove> validMoves = new ArrayList<>();
        int x = position.getColumn();
        int y = position.getRow();

        ChessMove move;
        ChessPosition originalPosition = new ChessPosition(y, x);

        // Up & Right
        int original_x = x; // temp
        int original_y = y; // temp
        while (x < 8 && y < 8) {
             x++;
             y++;
             move = new ChessMove(originalPosition, new ChessPosition(y, x), null);
             String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y, x), teamColor);
             if (checkPiece.equals("teammate")){
                 break;
             } else if (checkPiece.equals("enemy")){
                 validMoves.add(move);
                 break;
             }
            validMoves.add(move);
        }

        // Down & Right
        x = original_x;
        y = original_y;
        while (x < 8 && y > 1) {
            x++;
            y--;
            move = new ChessMove(originalPosition, new ChessPosition(y, x), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y, x), teamColor);
            if (checkPiece.equals("teammate")){
                break;
            } else if (checkPiece.equals("enemy")){
                validMoves.add(move);
                break;
            }
            validMoves.add(move);
        }

        // Down & Left
        x = original_x;
        y = original_y;
        while (x > 1 && y > 1) {
            x--;
            y--;
            move = new ChessMove(originalPosition, new ChessPosition(y, x), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y, x), teamColor);
            if (checkPiece.equals("teammate")){
                break;
            } else if (checkPiece.equals("enemy")){
                validMoves.add(move);
                break;
            }
            validMoves.add(move);
        }

        // Up & Left
        x = original_x;
        y = original_y;
        while (x > 1 && y < 8) {
            x--;
            y++;
            move = new ChessMove(originalPosition, new ChessPosition(y, x), null);
            String checkPiece = ChessPieceCalculator.checkForPiece(board, new ChessPosition(y, x), teamColor);
            if (checkPiece.equals("teammate")){
                break;
            } else if (checkPiece.equals("enemy")){
                validMoves.add(move);
                break;
            }
            validMoves.add(move);
        }
        return validMoves;
    }
}
