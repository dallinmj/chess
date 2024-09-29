package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceCalculator {

    public static Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece){
        Collection<ChessMove> moves = new ArrayList<>();

        if (piece.getPieceType() == ChessPiece.PieceType.BISHOP){
            moves = BishopCalculator.potentialMoves(board, position, piece);
        } else if (piece.getPieceType() == ChessPiece.PieceType.KING){
            moves = KingCalculator.potentialMoves(board, position, piece);
        } else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT){
            moves = KnightCalculator.potentialMoves(board, position, piece);
        } else if (piece.getPieceType() == ChessPiece.PieceType.PAWN){
            moves = PawnCalculator.potentialMoves(board, position, piece);
        } else if (piece.getPieceType() == ChessPiece.PieceType.ROOK){
            moves = RookCalculator.potentialMoves(board, position, piece);
        } else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN){
            moves.addAll(RookCalculator.potentialMoves(board, position, piece));
            moves.addAll(BishopCalculator.potentialMoves(board, position, piece));
        }
        return moves;
    }

    public static String checkMove(ChessBoard board, ChessPosition position, ChessPiece piece) {
        ChessPiece newPiece = board.getPiece(position);
        if (newPiece == null) {
            return "empty";
        } else {
            ChessGame.TeamColor newPieceColor = newPiece.getTeamColor();
            if (newPieceColor == piece.getTeamColor()){
                return "teammate";
            } else {
                return "enemy";
            }
        }
    }
}
