package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public record ChessMove(ChessPosition getStartPosition, ChessPosition getEndPosition, ChessPiece.PieceType promotionPiece) {
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof ChessMove chessMove)) {return false;}
        return Objects.equals(getStartPosition, chessMove.getStartPosition) &&
                Objects.equals(getEndPosition, chessMove.getEndPosition) &&
                promotionPiece == chessMove.promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return getStartPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return getEndPosition;
    }

    @Override
    public String toString() {
        return getStartPosition.toString() +
                getEndPosition.toString() +
                promotionPiece;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    @Override
    public ChessPiece.PieceType promotionPiece() {
        return promotionPiece;
    }
}
