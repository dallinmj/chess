package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand {

    private final ChessMove move;

    public MakeMoveCommand(ChessMove move) {
        this.move = move;
    }
}
