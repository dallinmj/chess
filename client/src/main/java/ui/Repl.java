package ui;

import chess.ChessBoard;
import network.ServerMessageObserver;
import websocket.messages.ServerMessage;

import java.util.Scanner;

public class Repl implements ServerMessageObserver {
    private Client client;
    private final String serverUrl;

    public Repl(String serverUrl) {
        client = new ClientLoggedOut(serverUrl);
        this.serverUrl = serverUrl;
    }

    public void run() {
        client.run();

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);

                if (result.contains("token:")) {
                    var token = result.split(":")[1];
                    this.client = new ClientLoggedIn(serverUrl, token);
                    this.client.run();
                    result = "";
                }
                if (result.equals("logout")) {
                    this.client = new ClientLoggedOut(serverUrl);
                    this.client.run();
                    result = "";
                }
                if (result.contains("board")) {
                    System.out.println(result);
                    var gameId = result.split(" ")[1];
                    var color = result.split(" ")[2];
                    var auth = result.split(" ")[3];
                    this.client = new ClientInGame(serverUrl, gameId, color, auth);
                    Chessboard.run(null, null);
                    result = "";
                }

                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n>>> ");
    }

    @Override
    public void notify(ServerMessage message) {
        System.out.println(message);
    }
}