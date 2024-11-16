package ui;

import dataaccess.DataAccessException;
import network.ServerFacade;
import requestresult.userrequestresult.LoginRequest;
import requestresult.userrequestresult.RegisterRequest;

import java.util.Arrays;

public class ClientLoggedOut implements Client {

    public ClientLoggedOut(String serverURL) {
        ServerFacade server = new ServerFacade(serverURL);

    }

    public void run() {
        System.out.println("Welcome to Chess! Please log in or register.");
        System.out.println(help());
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (DataAccessException ex) {
            return ex.getMessage();
        }
    }

    public String help() {
        return """
                Commands:
                login <username> <password>
                register <username> <password> <email>
                help
                quit""";
    }

    public String login(String... params) throws DataAccessException {
        if (params.length >= 2) {
            var username = params[0];
            var password = params[1];
            var request = ServerFacade.login(new LoginRequest(username, password));
            String token = request.authToken();
            return "token:" + token;
        }
        throw new DataAccessException("Expected: <username> <password>\n");
    }

    public String register(String... params) throws DataAccessException {
        if (params.length >= 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            var request = ServerFacade.register(new RegisterRequest(username, password, email));
            String token = request.authToken();
            return "token:" + token;
        }
        throw new DataAccessException("Expected: <username> <password> <email>");
    }
}
