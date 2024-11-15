package ui;

import java.util.Scanner;

public class Repl {
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
                if (result.equals("board")) {
//                    var board = new Chessboard();
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
}