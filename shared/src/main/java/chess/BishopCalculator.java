package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
Calculate the potential moves for the Bishop piece depending on its position and other pieces
 */
public class BishopCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position){
        //Import needed info (Location, board)
        Collection<ChessMove> validMoves = new ArrayList<>();
        List<int[]> coordinates = new ArrayList<>();
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
             validMoves.add(move);
             coordinates.add(new int[]{y, x});
        }

        // Down & Right
        x = original_x;
        y = original_y;
        while (x < 8 && y > 1) {
            x++;
            y--;
            move = new ChessMove(originalPosition, new ChessPosition(y, x), null);
            validMoves.add(move);
            coordinates.add(new int[]{y, x});
        }

        // Down & Left
        x = original_x;
        y = original_y;
        while (x > 1 && y > 1) {
            x--;
            y--;
            move = new ChessMove(originalPosition, new ChessPosition(y, x), null);
            validMoves.add(move);
            coordinates.add(new int[]{y, x});
        }

        // Up & Left
        x = original_x;
        y = original_y;
        while (x > 1 && y < 8) {
            x--;
            y++;
            move = new ChessMove(originalPosition, new ChessPosition(y, x), null);
            validMoves.add(move);
            coordinates.add(new int[]{y, x});
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
    public int checkForPiece(ChessBoard board, ChessPosition position){
//        boolean piece = board.getPiece(new ChessPosition(position.getRow(), position.getColumn())) != null;
        ChessPiece piece = board.getPiece(position);
        if (piece != null){
            // Compare Bishop team with piece team
            return 1;
        }
        return 0;
    }
}
