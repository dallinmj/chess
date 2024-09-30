package chess;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // Get potential moves for the given piece
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> potentialMoves = piece.pieceMoves(board, startPosition);
        TeamColor teamColor = piece.getTeamColor();

        // Filter out moves that would put the team in check
        Collection<ChessMove> validMoves = potentialMoves;
            // Iterate through potential moves and remove those that would put the team in check
        for (ChessMove move: potentialMoves) {
            // Test the move, throw exception if invalid
            try {
                makeMove(move, true);
            } catch (InvalidMoveException e) {
                validMoves.remove(move);
            }
        }

        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move, Boolean testMove) throws InvalidMoveException {
        ChessBoard copy = board.deepCopy();
        ChessPiece piece = board.getPiece(move.startPosition());
        ChessPiece replacedPiece = board.getPiece(move.endPosition());

        board.addPiece(move.endPosition(), piece);
        board.addPiece(move.startPosition(), null);

        if (isInCheck(piece.getTeamColor())) {
            undoLastMove(move, replacedPiece);
            throw new InvalidMoveException();
        }
        if (testMove){
            undoLastMove(move, replacedPiece);
        }
    }

    /**
     * Undoes the last move made
     */
    public void undoLastMove(ChessMove move, ChessPiece replacedPiece) {
        board.addPiece(move.startPosition(), board.getPiece(move.endPosition()));
        board.addPiece(move.endPosition(), replacedPiece);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }
}
