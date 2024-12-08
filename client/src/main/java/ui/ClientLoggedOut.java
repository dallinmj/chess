package ui;

import network.ResponseException;
import network.ServerFacade;
import requestresult.userrequestresult.LoginRequest;
import requestresult.userrequestresult.RegisterRequest;
import requestresult.userrequestresult.RegisterResult;

import java.util.Arrays;
import java.util.Objects;

public class ClientLoggedOut implements Client {

    private final ServerFacade server;

    public ClientLoggedOut(String serverURL) throws ResponseException {
        this.server = new ServerFacade(serverURL);

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
        } catch (ResponseException ex) {
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

    public String login(String... params) throws ResponseException {
        if (params.length >= 2) {
            try {
                var username = params[0];
                var password = params[1];
                var request = server.login(new LoginRequest(username, password));
                String token = request.authToken();
                return "token:" + token;
            } catch (Exception e) {
                return "Invalid username or password";
            }
        }
        throw new ResponseException("Expected: <username> <password>");
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            RegisterResult result;
            try {
                result = server.register(new RegisterRequest(username, password, email));
            } catch (Exception e){
                if (Objects.equals(e.getMessage(), "failure: 403 Forbidden")) {
                    return "Username already taken.";
                } else {
                    return e.getMessage();
                }
            }
            String token = result.authToken();
            return "token:" + token;
        }
        throw new ResponseException("Expected: <username> <password> <email>");
    }
}
