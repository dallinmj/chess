package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessPiece piece){
        Collection<ChessMove> moves = new ArrayList<>();

        int ogX = position.getColumn();
        int ogY = position.getRow();

        // Up
        int x = ogX;
        int y = ogY;
        while (y < 8){
            y++;
            ChessPosition newPosition = new ChessPosition(y, x);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (check.equals("teammate")){
                break;
            } else if (check.equals("enemy")){
                moves.add(move);
                break;
            }
            moves.add(move);
        }
        // Right
        y = ogY;
        while (x < 8){
            x++;
            ChessPosition newPosition = new ChessPosition(y, x);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (check.equals("teammate")){
                break;
            } else if (check.equals("enemy")){
                moves.add(move);
                break;
            }
            moves.add(move);
        }
        // Down
        x = ogX;
        while (y > 1){
            y--;
            ChessPosition newPosition = new ChessPosition(y, x);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (check.equals("teammate")){
                break;
            } else if (check.equals("enemy")){
                moves.add(move);
                break;
            }
            moves.add(move);
        }
        // Left
        y = ogY;
        while (x > 1){
            x--;
            ChessPosition newPosition = new ChessPosition(y, x);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (check.equals("teammate")){
                break;
            } else if (check.equals("enemy")){
                moves.add(move);
                break;
            }
            moves.add(move);
        }

        return moves;
    }
}
