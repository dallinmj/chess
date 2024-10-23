package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnCalculator {

    public static Collection<ChessMove> potentialMoves(ChessBoard board, ChessPosition position, ChessPiece piece){
        Collection<ChessMove> moves = new ArrayList<>();
        int upOrDown;
        int x = position.getColumn();
        int y = position.getRow();
        boolean doubleMove = false;

        ChessGame.TeamColor team = piece.getTeamColor();
        if (team == ChessGame.TeamColor.WHITE){
            upOrDown = 1;
            if (y == 2){
                doubleMove = true;
            }
        } else{
            upOrDown = -1;
            if (y == 7){
                doubleMove = true;
            }
        }
        // Up
        ChessPosition newPosition = new ChessPosition(y + upOrDown, x);
        int newX = newPosition.getColumn();
        int newY = newPosition.getRow();
        if (newY <= 8 && newY >= 1 && newX <= 8 && newX >= 1){
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (check.equals("empty")){
                moveUp(position, moves, newPosition, newY, move);
            }
        }
        // Up x2
        newPosition = new ChessPosition(y + (upOrDown * 2), x);
        newX = newPosition.getColumn();
        newY = newPosition.getRow();
        if (newY <= 8 && newY >= 1 && newX <= 8 && newX >= 1 && doubleMove){
            ChessMove move = new ChessMove(position, newPosition, null);
            ChessPosition closePosition = new ChessPosition(y + upOrDown, x);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            String checkClose = PieceCalculator.checkMove(board, closePosition, piece);
            if (check.equals("empty")){
                if (checkClose.equals("empty")){
                    moves.add(move);
                }
            }
        }
        // Up Right
        newPosition = new ChessPosition(y + upOrDown, x - upOrDown);
        initializeMove(board, position, piece, moves, newPosition);
        // Up Left
        newPosition = new ChessPosition(y + upOrDown, x + upOrDown);
        initializeMove(board, position, piece, moves, newPosition);
        return moves;
    }

    private static void initializeMove(ChessBoard board, ChessPosition position, ChessPiece piece, Collection<ChessMove> moves, ChessPosition newPosition) {
        int newX;
        int newY;
        newX = newPosition.getColumn();
        newY = newPosition.getRow();
        if (newY <= 8 && newY >= 1 && newX <= 8 && newX >= 1){
            ChessMove move = new ChessMove(position, newPosition, null);
            String check = PieceCalculator.checkMove(board, newPosition, piece);
            if (check.equals("enemy")){
                moveUp(position, moves, newPosition, newY, move);
            }
        }
    }

    private static void moveUp(ChessPosition position, Collection<ChessMove> moves, ChessPosition newPosition, int newY, ChessMove move) {
        if (newY == 1 || newY == 8){
            for (ChessPiece.PieceType type : ChessPiece.PieceType.values()){
                if (type == ChessPiece.PieceType.KING || type == ChessPiece.PieceType.PAWN){
                    continue;
                }
                move = new ChessMove(position, newPosition, type);
                moves.add(move);
            }
        } else {
            moves.add(move);
        }
    }
}
