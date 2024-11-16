package ui;

import network.ResponseException;
import network.ServerFacade;
import requestresult.gamerequestresult.CreateGameRequest;
import requestresult.gamerequestresult.JoinGameRequest;
import requestresult.gamerequestresult.ListGamesRequest;
import requestresult.userrequestresult.LogoutRequest;

import java.util.Arrays;
import java.util.Map;

public class ClientLoggedIn implements Client {

    private final String auth;
    private final Map<Integer, Integer> gameIDMap;

    public ClientLoggedIn(String serverURL, String auth) {
        ServerFacade server = new ServerFacade(serverURL);
        this.auth = auth;
        this.gameIDMap = new java.util.HashMap<>(Map.of());
    }

    public void run() {
        System.out.println(help());
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "logout" -> logout(params);
                case "create" -> createGame(params);
                case "list" -> listGames(params);
                case "join" -> playGame(params);
                case "observe" -> observeGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    private String help() {
        return """
                Commands:
                logout
                create <gameName>
                list
                join <gameID> [white|black]
                observe <gameID>
                help
                quit""";
    }

    private String logout(String... params) throws ResponseException {
        var token = ServerFacade.logout(new LogoutRequest(auth));
        return "logout";
        }

    private String createGame(String... params) throws ResponseException {
        if (params.length >= 1) {
            var gameName = params[0];
            var token = ServerFacade.createGame(new CreateGameRequest(auth, gameName));
            return "Game created!";
        }
        throw new ResponseException("Expected: <gameName>");
    }

    private String listGames(String... params) throws ResponseException {
        var result = ServerFacade.listGames(new ListGamesRequest(auth));
        var gamesList = result.games();
        StringBuilder games = new StringBuilder();

        int i = 0;
        for (var game : gamesList) {
            i++;
            gameIDMap.put(i, game.gameID());
            String prettyGame = "Id: " + i + " Name: <" + game.gameName() + "> White: <"
                    + game.whiteUsername() + "> Black: <" + game.blackUsername() + ">";
            games.append(prettyGame).append("\n");
        }
        return games.toString();
    }

    private String playGame(String... params) throws ResponseException {
        if (params.length >= 2 && (params[1].equals("white") || params[1].equals("black"))) {
            var fakeGameID = params[0];
            if (!gameIDMap.containsKey(Integer.parseInt(fakeGameID))) {
                throw new ResponseException("Invalid game ID");
            }
            var realGameID = gameIDMap.get(Integer.parseInt(fakeGameID));
            var color = params[1];
            ServerFacade.joinGame(new JoinGameRequest(auth, color, realGameID));
            return "board";
        }
        throw new ResponseException("Expected: <gameID> [white|black]");
    }

    private String observeGame(String... params) throws ResponseException {
        if (params.length >= 1 && gameIDMap.containsKey(Integer.parseInt(params[0]))) {
            var gameID = params[0];
            return "board";
        }
        throw new ResponseException("Expected: <gameID>");
    }
}
