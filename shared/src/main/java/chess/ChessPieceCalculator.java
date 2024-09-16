package chess;

import java.util.Collection;

public class ChessPieceCalculator {


    public static Collection<ChessMove> Bishop(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        return BishopCalculator.potentialMoves(board, position, teamColor);
    }
    public static Collection<ChessMove> King(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        return KingCalculator.potentialMoves(board, position, teamColor);
    }
    public static Collection<ChessMove> Knight(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        return KnightCalculator.potentialMoves(board, position, teamColor);
    }
    /*
    Check to see if there is a piece on the potential move
     */
    public static String checkForPiece(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor){
        ChessPiece piece = board.getPiece(position);
        if (piece != null){
            // Compare our team with other piece team
            if (piece.getTeamColor() == teamColor){
                return "teammate";
            }
            return "enemy";
        }
        return "empty";
    }
}
