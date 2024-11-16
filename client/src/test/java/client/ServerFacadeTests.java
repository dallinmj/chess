package client;

import network.ResponseException;
import network.ServerFacade;
import org.junit.jupiter.api.*;
import requestresult.gamerequestresult.CreateGameResult;
import requestresult.userrequestresult.LoginRequest;
import requestresult.userrequestresult.LogoutRequest;
import requestresult.userrequestresult.RegisterRequest;
import requestresult.userrequestresult.RegisterResult;
import server.Server;
import requestresult.ClearRequest;
import requestresult.gamerequestresult.CreateGameRequest;
import requestresult.gamerequestresult.JoinGameRequest;
import requestresult.gamerequestresult.ListGamesRequest;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void clear() throws ResponseException {
         ServerFacade.clear(new ClearRequest());
    }


    @Test
    public void loginTest() throws ResponseException {
        ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        LoginRequest request = new LoginRequest("doug", "goodpassword");
        var result = ServerFacade.login(request);
        Assertions.assertNotNull(result);
    }

    @Test
    public void badLoginTest() throws ResponseException {
        ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        LoginRequest request = new LoginRequest("doug", "badpassword");
        Assertions.assertThrows(ResponseException.class, () -> ServerFacade.login(request));
    }

    @Test
    public void registerTest() throws ResponseException {
        RegisterRequest request = new RegisterRequest("doug", "goodpassword", "Diggyemail");
        var result = ServerFacade.register(request);
        Assertions.assertNotNull(result);
    }

    @Test
    public void badRegisterTest() throws ResponseException {
        RegisterRequest request = new RegisterRequest("doug", "goodpassword", "Diggyemail");
        ServerFacade.register(request);
        Assertions.assertThrows(ResponseException.class, () -> ServerFacade.register(request));
    }

    @Test
    public void logoutTest() throws ResponseException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();

        LogoutRequest logoutRequest = new LogoutRequest(auth);
        var result = ServerFacade.logout(logoutRequest);
        Assertions.assertNotNull(result);
    }

    @Test
    public void badLogoutTest() throws ResponseException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();

        LogoutRequest logoutRequest = new LogoutRequest(auth);
        ServerFacade.logout(logoutRequest);
        Assertions.assertThrows(ResponseException.class, () -> ServerFacade.logout(logoutRequest));
    }

    @Test
    public void createGameTest() throws ResponseException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "gamename!");
        var createGameResult = ServerFacade.createGame(createGameRequest);
        Assertions.assertNotNull(createGameResult);
    }

    @Test
    public void badCreateGameTest() throws ResponseException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, null);
        Assertions.assertThrows(ResponseException.class, () -> ServerFacade.createGame(createGameRequest));
    }

    @Test
    public void joinGameTest() throws ResponseException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "gamename!");
        var createGameResult = ServerFacade.createGame(createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest(auth, "white", createGameResult.gameID());
        var joinGameResult = ServerFacade.joinGame(joinGameRequest);
        Assertions.assertNotNull(joinGameResult);
    }

    @Test
    public void badJoinGameTest() throws ResponseException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameResult createGameResult = ServerFacade.createGame(new CreateGameRequest(auth, "gamename!"));
        int gameID = createGameResult.gameID();
        JoinGameRequest joinGameRequest = new JoinGameRequest(auth, "white", gameID);
        ServerFacade.joinGame(joinGameRequest);
        Assertions.assertThrows(ResponseException.class, () -> ServerFacade.joinGame(joinGameRequest));
    }

    @Test
    public void listGamesTest() throws ResponseException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("new", "newpass", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "newgame");
        var createGameResult = ServerFacade.createGame(createGameRequest);
        CreateGameRequest createGameRequest2 = new CreateGameRequest(auth, "antohergame");
        var createGameResult2 = ServerFacade.createGame(createGameRequest);
        ListGamesRequest listGamesRequest = new ListGamesRequest(auth);
        var listGamesResult = ServerFacade.listGames(listGamesRequest);
        Assertions.assertNotNull(listGamesResult);
    }

    @Test
    public void badListGamesTest() throws ResponseException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("new", "newpass", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "newgame");
        var createGameResult = ServerFacade.createGame(createGameRequest);
        CreateGameRequest createGameRequest2 = new CreateGameRequest(auth, "antohergame");
        var createGameResult2 = ServerFacade.createGame(createGameRequest);
        ListGamesRequest listGamesRequest = new ListGamesRequest("auth");
        Assertions.assertThrows(ResponseException.class, () -> ServerFacade.listGames(listGamesRequest));
    }
}
