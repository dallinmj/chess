package network;

import dataaccess.DataAccessException;
import service.requestresult.ClearRequest;
import service.requestresult.ClearResult;
import service.requestresult.gamerequestresult.*;
import service.requestresult.userrequestresult.*;

public class ServerFacade {

    private static ClientCommunicator clientCommunicator;

    public ServerFacade(String serverURL) {
        clientCommunicator = new ClientCommunicator(serverURL);
    }

    public static LoginResult login(LoginRequest request) throws DataAccessException {
        return clientCommunicator.makeRequest("POST", "/session", request, LoginResult.class);
    }

    public static LogoutResult logout(LogoutRequest request) throws DataAccessException {
        return clientCommunicator.makeRequest("DELETE", "/session", request, LogoutResult.class);
    }

    public static RegisterResult register(RegisterRequest request) throws DataAccessException {
        return clientCommunicator.makeRequest("POST", "/user", request, RegisterResult.class);
    }

    public static JoinGameResult joinGame(JoinGameRequest request) throws DataAccessException {
        return clientCommunicator.makeRequest("PUT", "/game", request, JoinGameResult.class);
    }

    public static CreateGameResult createGame(CreateGameRequest request) throws DataAccessException {
        return clientCommunicator.makeRequest("POST", "/game", request, CreateGameResult.class);
    }

    public static ListGamesResult listGames(ListGamesRequest request) throws DataAccessException {
        return clientCommunicator.makeRequest("GET", "/game", request, ListGamesResult.class);
    }

    public static void clear(ClearRequest request) throws DataAccessException {
        clientCommunicator.makeRequest("DELETE", "/db", request, ClearResult.class);
    }
}
