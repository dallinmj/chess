package chess;

import java.util.ArrayList;
import java.util.Collection;

/*
Calculate the potential moves for the Bishop piece depending on its position and other pieces
 */
public class BishopCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        //Import needed info (Location, board)
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
             String checkPiece = checkForPiece(board, new ChessPosition(y, x), teamColor);
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
            String checkPiece = checkForPiece(board, new ChessPosition(y, x), teamColor);
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
            String checkPiece = checkForPiece(board, new ChessPosition(y, x), teamColor);
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
            String checkPiece = checkForPiece(board, new ChessPosition(y, x), teamColor);
            if (checkPiece.equals("teammate")){
                break;
            } else if (checkPiece.equals("enemy")){
                validMoves.add(move);
                break;
            }
            validMoves.add(move);
        }

//        new int[][]{
//                {6, 5}, {7, 6}, {8, 7}, up right
//                {4, 5}, {3, 6}, {2, 7}, {1, 8}, down right
//                {4, 3}, {3, 2}, {2, 1}, down left
//                {6, 3}, {7, 2}, {8, 1}, up left
//        }

        return validMoves;
    }
    /*
    Check to see if there is a piece on the potential move
     */
    public static String checkForPiece(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor){
//        boolean piece = board.getPiece(new ChessPosition(position.getRow(), position.getColumn())) != null;
        ChessPiece piece = board.getPiece(position);
        if (piece != null){
            // Compare Bishop team with piece team
            if (piece.getTeamColor() == teamColor){
                return "teammate";
            }
            return "enemy";
        }
        return "empty";
    }
}
