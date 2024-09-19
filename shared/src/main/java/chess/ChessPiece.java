package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", pieceType=" + pieceType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Make variable with pieceType
        PieceType pieceType = getPieceType();
        Collection<ChessMove> moves = new ArrayList<>();
        if (pieceType == PieceType.BISHOP) {
            moves = ChessPieceCalculator.Bishop(board, myPosition, pieceColor);
        } else if (pieceType == PieceType.KING) {
            moves = ChessPieceCalculator.King(board, myPosition, pieceColor);
        } else if (pieceType == PieceType.KNIGHT) {
            moves = ChessPieceCalculator.Knight(board, myPosition, pieceColor);
        } else if (pieceType == PieceType.PAWN) {
            moves = ChessPieceCalculator.Pawn(board, myPosition, pieceColor);
        } else if (pieceType == PieceType.QUEEN) {
            // Queen is a combination of rook and bishop
            moves.addAll(ChessPieceCalculator.Bishop(board, myPosition, pieceColor));
            moves.addAll(ChessPieceCalculator.Rook(board, myPosition, pieceColor));
        } else if (pieceType == PieceType.ROOK) {
            moves = ChessPieceCalculator.Rook(board, myPosition, pieceColor);

        }

        return moves;
    }
}
