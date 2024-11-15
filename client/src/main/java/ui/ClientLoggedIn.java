package ui;

import dataaccess.DataAccessException;
import network.ServerFacade;
import service.requestresult.gamerequestresult.CreateGameRequest;
import service.requestresult.gamerequestresult.JoinGameRequest;
import service.requestresult.gamerequestresult.ListGamesRequest;
import service.requestresult.userrequestresult.LoginRequest;
import service.requestresult.userrequestresult.LogoutRequest;
import service.requestresult.userrequestresult.RegisterRequest;

import java.util.Arrays;
import java.util.Map;

public class ClientLoggedIn implements Client {

    private final String auth;
    private Map<Integer, Integer> gameIDMap;

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
                case "create" -> create_game(params);
                case "list" -> list_games(params);
                case "join" -> play_game(params);
                case "observe" -> observe_game(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (DataAccessException ex) {
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

    private String logout(String... params) throws DataAccessException {
        var token = ServerFacade.logout(new LogoutRequest(auth));
        return "logout";
        }

    private String create_game(String... params) throws DataAccessException {
        if (params.length >= 1) {
            var gameName = params[0];
            var token = ServerFacade.createGame(new CreateGameRequest(auth, gameName));
            return "Game created!";
        }
        throw new DataAccessException("Expected: <gameName>");
    }

    private String list_games(String... params) throws DataAccessException {
        var result = ServerFacade.listGames(new ListGamesRequest(auth));
        var games_list = result.games();
        StringBuilder games = new StringBuilder();

        int i = 0;
        for (var game : games_list) {
            i++;
            gameIDMap.put(i, game.gameID());
            String prettyGame = STR."Id: <\{i}> Name: <\{game.gameName()}> White: <\{game.whiteUsername()}> Black: <\{game.blackUsername()}>";
            games.append(prettyGame).append("\n");
        }
        return games.toString();
    }

    private String play_game(String... params) throws DataAccessException {
        if (params.length >= 2 && (params[1].equals("white") || params[1].equals("black"))) {
            var fakeGameID = params[0];
            if (!gameIDMap.containsKey(Integer.parseInt(fakeGameID))) {
                throw new DataAccessException("Invalid game ID");
            }
            var realGameID = gameIDMap.get(Integer.parseInt(fakeGameID));
            var color = params[1];
            ServerFacade.joinGame(new JoinGameRequest(auth, color, realGameID));
            return "board";
        }
        throw new DataAccessException("Expected: <gameID> [white|black]");
    }

    private String observe_game(String... params) throws DataAccessException {
        if (params.length >= 1 && gameIDMap.containsKey(Integer.parseInt(params[0]))) {
            var gameID = params[0];
            return "board";
        }
        throw new DataAccessException("Expected: <gameID>");
    }
}
