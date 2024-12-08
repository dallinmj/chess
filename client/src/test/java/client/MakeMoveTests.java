package client;

import chess.ChessBoard;
import chess.ChessGame;
import network.ResponseException;
import org.junit.jupiter.api.Test;
import ui.Chessboard;

import java.util.Arrays;

public class MakeMoveTests {

    @Test
    public void testMakeMove() throws ResponseException {
        String from = "a3";
        String to = "a2";
//        ClientInGame.makeMoveTest(from, to);
    }

    @Test
    public void chessBoardTest() {
        ChessGame game = new ChessGame();
        ChessBoard board = game.getBoard();
        System.out.println(Arrays.toString(board.getSquares()[0]));
        Chessboard.run(board.getSquares(), ChessGame.TeamColor.BLACK);
    }
}
