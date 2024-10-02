package chess;

import java.util.ArrayList;
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
            undoMove(move, replacedPiece);
            throw new InvalidMoveException();
        }
        if (testMove){
            undoMove(move, replacedPiece);
        }
    }
    /**
     * Overload for makeMove for one parameter
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        makeMove(move, false);
    }

    /**
     * Undoes the last move made
     */
    public void undoMove(ChessMove move, ChessPiece replacedPiece) {
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
        Collection<ChessPiece> enemyPieces = new ArrayList<>(); // Calculate moves for each piece and add all of them to a massive list, see if any of those = kingPosition :)
        Collection<ChessPosition> enemyPotentialPositions = new ArrayList<>();
        boolean inCheck = false;

        // Iterate through all values to find the king and to calculate all enemy moves
        for (int i = 0; i < 8; i++){ // y values
            for (int j = 0; j < 8; j++){ // x values
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor){
                    ChessPosition kingPosition = position;
                }
            }
        }

        return inCheck;
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
