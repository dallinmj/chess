package network;

import service.requestresult.ClearRequest;
import service.requestresult.ClearResult;
import service.requestresult.gamerequestresult.*;
import service.requestresult.userrequestresult.*;

public class ServerFacade {

    private final ClientCommunicator clientCommunicator;

    public ServerFacade(String serverURL) {
        clientCommunicator = new ClientCommunicator(serverURL);
    }

    public static LoginResult login(LoginRequest request) {
        // http url connection class
        // create request that calls register api
        clientCommunicator.makeRequest("POST", "/login", request, LoginResult.class);
        return null;
    }

    public static LogoutResult logout(LogoutRequest request) {
        return null;
    }

    public static RegisterResult register(RegisterRequest request) {
        return null;
    }

    public static JoinGameResult joinGame(JoinGameRequest request) {
        return null;
    }

    public static CreateGameResult createGame(CreateGameRequest request) {
        return null;
    }

    public static ListGamesResult listGames(ListGamesRequest request) {
        return null;
    }

    public static ClearResult clear(ClearRequest request) {
        return null;
    }
}
