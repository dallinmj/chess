package ui;

import java.util.Arrays;

public class ClientLoggedOut implements Client {
    private final MainClient client;

    public ClientLoggedOut(String serverURL) {
        client = new MainClient(serverURL);
    }

    public void run() {
        System.out.println("Welcome to Chess! Please log in or register.");

    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login();
                case "register" -> register();
                case "help" -> help(); //Implement all these methods here!!!
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public
}
