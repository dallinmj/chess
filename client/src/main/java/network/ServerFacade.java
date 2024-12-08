package network;

import chess.ChessMove;
import requestresult.ClearRequest;
import requestresult.ClearResult;
import requestresult.gamerequestresult.*;
import requestresult.userrequestresult.*;

public class ServerFacade {

    private static ClientCommunicator clientCommunicator;
    private static WebsocketCommunicator websocketCommunicator;
    private ServerMessageObserver serverMessageObserver;

    public ServerFacade(String serverURL) throws ResponseException {
        clientCommunicator = new ClientCommunicator(serverURL);
        websocketCommunicator = new WebsocketCommunicator(serverURL, null);
    }

    public ServerFacade(String serverURL, ServerMessageObserver serverMessageObserver) throws ResponseException {
        clientCommunicator = new ClientCommunicator(serverURL);
        this.serverMessageObserver = serverMessageObserver;
        websocketCommunicator = new WebsocketCommunicator(serverURL, serverMessageObserver);
    }

    public void connect(String auth, int gameId, String color) throws ResponseException {
        websocketCommunicator.connect(auth, gameId, color);
    }

    public void makeMove(String auth, int gameId, ChessMove move) throws ResponseException {
        websocketCommunicator.makeMove(auth, gameId, move);
    }

    public void leave(String auth, int gameId, String color) throws ResponseException {
        websocketCommunicator.leave(auth, gameId, color);
    }

    public void resign(String auth, int gameId, String color) throws ResponseException {
        websocketCommunicator.resign(auth, gameId, color);
    }

    public LoginResult login(LoginRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("POST", "/session", request, LoginResult.class);
    }

    public LogoutResult logout(LogoutRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("DELETE", "/session", request, LogoutResult.class);
    }

    public RegisterResult register(RegisterRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("POST", "/user", request, RegisterResult.class);
    }

    public JoinGameResult joinGame(JoinGameRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("PUT", "/game", request, JoinGameResult.class);
    }

    public CreateGameResult createGame(CreateGameRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("POST", "/game", request, CreateGameResult.class);
    }

    public ListGamesResult listGames(ListGamesRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("GET", "/game", request, ListGamesResult.class);
    }

    public void clear(ClearRequest request) throws ResponseException {
        clientCommunicator.makeRequest("DELETE", "/db", request, ClearResult.class);
    }
}
