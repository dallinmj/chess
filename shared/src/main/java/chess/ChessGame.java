package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor colorTurn;
    private boolean gameOver;

    public ChessGame() {
        this.board = new ChessBoard();
        this.board.resetBoard();
        this.colorTurn = TeamColor.WHITE;
        this.gameOver = false;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return colorTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        colorTurn = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessGame chessGame)) {
            return false;
        }
        return Objects.equals(board, chessGame.board) && colorTurn == chessGame.colorTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, colorTurn);
    }

    public void end() {
        this.gameOver = true;
    }

    public boolean isOver() {
        return this.gameOver;
    }

    public boolean eitherTeamInCheck() {
        return isInCheck(TeamColor.WHITE) || isInCheck(TeamColor.BLACK);
    }

    public void isCorrectPlayer(ChessMove move, String playerColor) throws InvalidMoveException {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        String pieceColor = piece.getTeamColor().toString().toLowerCase();
        if (!playerColor.equals(pieceColor)) {
            throw new InvalidMoveException("Invalid move, not your piece");
        }
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
     * getStartPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // Get potential moves for the given piece
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> potentialMoves = piece.pieceMoves(board, startPosition);

        // Filter out moves that would put the team in check
        Collection<ChessMove> validMoves = new ArrayList<>(potentialMoves);
            // Iterate through potential moves and remove those that would put the team in check
        for (ChessMove move : potentialMoves) {
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
        ChessPiece piece = board.getPiece(move.getStartPosition());
        ChessPiece replacedPiece = board.getPiece(move.getEndPosition());
        boolean valid = false;

        if (gameOver) {
            throw new InvalidMoveException("Game is over");
        }

        // Piece doesn't exist at given location
        if (piece == null){
            throw new InvalidMoveException("Piece doesn't exist here");
        }

        // Check if move is valid
        for (ChessMove moveToCheck : piece.pieceMoves(board, move.getStartPosition())){
            if (moveToCheck.equals(move)){ valid = true; break; }
        }

        // Not a valid move
        if (!valid){
            throw new InvalidMoveException("Not a valid move");
        }

        // Changes promotion piece :)
        if (move.promotionPiece() != null) {
            piece = new ChessPiece(piece.getTeamColor(), move.promotionPiece());
        }

        // Makes the move
        board.addPiece(move.getEndPosition(), piece);
        board.addPiece(move.getStartPosition(), null);

        // Sees if their king is in check
        if (isInCheck(piece.getTeamColor())) {
            undoMove(move, replacedPiece);
            throw new InvalidMoveException();
        }
        if (testMove){
            undoMove(move, replacedPiece);
        } else if (this.getTeamTurn() != piece.getTeamColor()) {
            undoMove(move, replacedPiece);
            throw new InvalidMoveException("Not your turn bro");
        } else {
            if (this.getTeamTurn() == TeamColor.WHITE) {
                this.setTeamTurn(TeamColor.BLACK);
            } else {
                this.setTeamTurn(TeamColor.WHITE);
            }
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
        board.addPiece(move.getStartPosition(), board.getPiece(move.getEndPosition()));
        board.addPiece(move.getEndPosition(), replacedPiece);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // Calculate moves for each piece and add all of them to a massive list, see if any of those = kingPosition :)
        Collection<ChessMove> enemyPotentialMoves = new ArrayList<>();
        ChessPosition kingPosition = null;
        boolean inCheck = false;

        // Iterate through all values to find the king and to calculate all enemy moves
        for (int i = 1; i <= 8; i++){ // y values
            for (int j = 1; j <= 8; j++){ // x values
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);

                if (piece == null){ continue; }

                // Get king position
                if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor){
                    kingPosition = position;
                }

                // Get enemy moves
                if (piece.getTeamColor() != teamColor){
                    Collection<ChessMove> moves = piece.pieceMoves(board, position);
                    enemyPotentialMoves.addAll(moves);
                }
            }
        }

        // Calculate if in check
        for (ChessMove move : enemyPotentialMoves){
            if (move.getEndPosition().equals(kingPosition)) {
                inCheck = true;
                break;
            }
        }
        return inCheck;
    }

    /**
     * Helper method for stalemate and checkmate for calculating valid moves
     */
    private boolean hasValidMoves(TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) { // y values
            for (int j = 1; j <= 8; j++) { // x values
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);

                if (piece != null && teamColor == piece.getTeamColor()) {
                    Collection<ChessMove> moves = validMoves(position);
                    if (!moves.isEmpty()) { return true; }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean inCheckMate = !hasValidMoves(teamColor) && isInCheck(teamColor);
        if (inCheckMate) {
            end();
        }
        return inCheckMate;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (board.isEmpty()) { return false; }
        if (teamColor != getTeamTurn()) { return false; }
        return !isInCheck(teamColor) && !hasValidMoves(teamColor);
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
        return board;
    }
}
