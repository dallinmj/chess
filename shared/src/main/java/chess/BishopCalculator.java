package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessPiece piece){
        Collection<ChessMove> moves = new ArrayList<>();

        int ogX = position.getColumn();
        int ogY = position.getRow();

        // Up Right
        int x = ogX;
        int y = ogY;
        while (x < 8 && y < 8){
            x++;
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
        // Down Right
        x = ogX;
        y = ogY;
        while (x < 8 && y > 1){
            x++;
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
        // Down Left
        x = ogX;
        y = ogY;
        while (x > 1 && y > 1){
            x--;
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
        // Up Left
        x = ogX;
        y = ogY;
        while (x > 1 && y < 8){
            x--;
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

        return moves;
    }
}
