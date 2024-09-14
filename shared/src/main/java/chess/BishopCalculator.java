package chess;

import java.util.ArrayList;
import java.util.Collection;

/*
Calculate the potential moves for the Bishop piece depending on its position and other pieces
 */
public class BishopCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position){
        //Import needed info (Location, board)
        Collection<ChessMove> validMoves = new ArrayList<>();
        int x = position.getColumn();
        int y = position.getRow();

        ChessMove move;

        // Up & Right
        int X = x; // temp
        int Y = y; // temp
        while (x < 8 && y < 8) {
             x++;
             y++;
             move = new ChessMove(new ChessPosition(x - 1, y - 1), new ChessPosition(x, y), null);
             validMoves.add(move);
        }

        Collection<tuple> coordinates = new ArrayList<>();

        // Up & Left
        x = X;
        y = Y;
        while (x > 0 && y < 8) {
            x--;
            y++;
            move = new ChessMove(new ChessPosition(x + 1, y - 1), new ChessPosition(x, y), null);
            validMoves.add(move);
        }

        // Down & Right
        x = X;
        y = Y;
        while (x < 8 && y > 0) {
            x++;
            y--;
            move = new ChessMove(new ChessPosition(x - 1, y + 1), new ChessPosition(x, y), null);
            validMoves.add(move);
        }

        // Down & Left
        x = X;
        y = Y;
        while (x > 0 && y > 0) {
            x--;
            y--;
            move = new ChessMove(new ChessPosition(x + 1, y + 1), new ChessPosition(x, y), null);
            validMoves.add(move);
        }

//        new int[][]{
//                {6, 5}, {7, 6}, {8, 7},
//                {4, 5}, {3, 6}, {2, 7}, {1, 8},
//                {4, 3}, {3, 2}, {2, 1},
//                {6, 3}, {7, 2}, {8, 1},
//        }


        System.out.println(validMoves.toArray().length);
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
