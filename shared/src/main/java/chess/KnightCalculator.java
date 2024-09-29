package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessPiece piece){
        Collection<ChessMove> moves = new ArrayList<>();

        int x = position.getColumn();
        int y = position.getRow();

        // Up Right
        if (y < 7 && x < 8){
            ChessPosition newPosition = new ChessPosition(y + 2, x + 1);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Right Up
        if (y < 8 && x < 7){
            ChessPosition newPosition = new ChessPosition(y + 1, x + 2);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Right Down
        if (y > 1 && x < 7){
            ChessPosition newPosition = new ChessPosition(y - 1, x + 2);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Down Right
        if (y > 2 && x < 8){
            ChessPosition newPosition = new ChessPosition(y - 2, x + 1);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Down Left
        if (y > 2 && x > 1){
            ChessPosition newPosition = new ChessPosition(y - 2, x - 1);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Left Down
        if (y > 1 && x > 2){
            ChessPosition newPosition = new ChessPosition(y - 1, x - 2);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Left Up
        if (y < 8 && x > 2){
            ChessPosition newPosition = new ChessPosition(y + 1, x - 2);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }
        // Up Left
        if (y < 7 && x > 1){
            ChessPosition newPosition = new ChessPosition(y + 2, x - 1);
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (!check.equals("teammate")){
                moves.add(move);
            }
        }


        return moves;
    }
}
