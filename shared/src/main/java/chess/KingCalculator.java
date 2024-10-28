package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessPiece piece){
        Collection<ChessMove> moves = new ArrayList<>();

        int x = position.getColumn();
        int y = position.getRow();

        // Up
        if (y < 8){
            ChessPosition newPosition = new ChessPosition(y + 1, x);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Right
        if (x < 8){
            ChessPosition newPosition = new ChessPosition(y, x + 1);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Down
        if (y > 1){
            ChessPosition newPosition = new ChessPosition(y - 1, x);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Left
        if (x > 1){
            ChessPosition newPosition = new ChessPosition(y, x - 1);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Up Right
        if (y < 8 && x < 8){
            ChessPosition newPosition = new ChessPosition(y + 1, x + 1);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Down Right
        downRight(board, position, piece, moves, x, y, 8, 1);

        // Down Left
        if (y > 1 && x > 1){
            ChessPosition newPosition = new ChessPosition(y - 1, x - 1);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Up Left
        if (y < 8 && x > 1){
            ChessPosition newPosition = new ChessPosition(y + 1, x - 1);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        return moves;
    }

    static void downRight(ChessBoard board, ChessPosition position, ChessPiece piece, Collection<ChessMove> moves, int x, int y, int i, int i2) {
        if (y > 1 && x < i){
            ChessPosition newPosition = new ChessPosition(y - 1, x + i2);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
    }
}
