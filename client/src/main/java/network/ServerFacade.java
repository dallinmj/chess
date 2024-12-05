package network;

import chess.ChessMove;
import requestresult.ClearRequest;
import requestresult.ClearResult;
import requestresult.gamerequestresult.*;
import requestresult.userrequestresult.*;
import websocket.messages.ServerMessage;

public class ServerFacade {

    private static ClientCommunicator clientCommunicator;
    private static WebsocketCommunicator websocketCommunicator;

    public ServerFacade(String serverURL) throws ResponseException {
        clientCommunicator = new ClientCommunicator(serverURL);

        ServerMessageObserver serverMessageObserver = new ServerMessageObserver() {
            @Override
            public void notify(ServerMessage message) {
                System.out.println(message);
            }
        };
        websocketCommunicator = new WebsocketCommunicator(serverURL, serverMessageObserver);
    }

    public static void connect(String auth, int gameId) throws ResponseException {
        websocketCommunicator.connect(auth, gameId);
    }

    public static void makeMove(String auth, int gameId, ChessMove move) throws ResponseException {
        websocketCommunicator.makeMove(auth, gameId, move);
    }

    public static void leave(String auth, int gameId) throws ResponseException {
        websocketCommunicator.leave(auth, gameId);
    }

    public static void resign(String auth, int gameId) throws ResponseException {
        websocketCommunicator.resign(auth, gameId);
    }

    public static LoginResult login(LoginRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("POST", "/session", request, LoginResult.class);
    }

    public static LogoutResult logout(LogoutRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("DELETE", "/session", request, LogoutResult.class);
    }

    public static RegisterResult register(RegisterRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("POST", "/user", request, RegisterResult.class);
    }

    public static JoinGameResult joinGame(JoinGameRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("PUT", "/game", request, JoinGameResult.class);
    }

    public static CreateGameResult createGame(CreateGameRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("POST", "/game", request, CreateGameResult.class);
    }

    public static ListGamesResult listGames(ListGamesRequest request) throws ResponseException {
        return clientCommunicator.makeRequest("GET", "/game", request, ListGamesResult.class);
    }

    public static void clear(ClearRequest request) throws ResponseException {
        clientCommunicator.makeRequest("DELETE", "/db", request, ClearResult.class);
    }
}
