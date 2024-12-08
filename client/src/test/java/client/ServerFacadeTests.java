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
    private static ServerFacade facade;

    @BeforeAll
    public static void init() throws ResponseException {
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
         facade.clear(new ClearRequest());
    }


    @Test
    public void loginTest() throws ResponseException {
        facade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        LoginRequest request = new LoginRequest("doug", "goodpassword");
        var result = facade.login(request);
        Assertions.assertNotNull(result);
    }

    @Test
    public void badLoginTest() throws ResponseException {
        facade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        LoginRequest request = new LoginRequest("doug", "badpassword");
        Assertions.assertThrows(ResponseException.class, () -> facade.login(request));
    }

    @Test
    public void registerTest() throws ResponseException {
        RegisterRequest request = new RegisterRequest("doug", "goodpassword", "Diggyemail");
        var result = facade.register(request);
        Assertions.assertNotNull(result);
    }

    @Test
    public void badRegisterTest() throws ResponseException {
        RegisterRequest request = new RegisterRequest("doug", "goodpassword", "Diggyemail");
        facade.register(request);
        Assertions.assertThrows(ResponseException.class, () -> facade.register(request));
    }

    @Test
    public void logoutTest() throws ResponseException {
        RegisterResult registerResult = facade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();

        LogoutRequest logoutRequest = new LogoutRequest(auth);
        var result = facade.logout(logoutRequest);
        Assertions.assertNotNull(result);
    }

    @Test
    public void badLogoutTest() throws ResponseException {
        RegisterResult registerResult = facade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();

        LogoutRequest logoutRequest = new LogoutRequest(auth);
        facade.logout(logoutRequest);
        Assertions.assertThrows(ResponseException.class, () -> facade.logout(logoutRequest));
    }

    @Test
    public void createGameTest() throws ResponseException {
        RegisterResult registerResult = facade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "gamename!");
        var createGameResult = facade.createGame(createGameRequest);
        Assertions.assertNotNull(createGameResult);
    }

    @Test
    public void badCreateGameTest() throws ResponseException {
        RegisterResult registerResult = facade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, null);
        Assertions.assertThrows(ResponseException.class, () -> facade.createGame(createGameRequest));
    }

    @Test
    public void joinGameTest() throws ResponseException {
        RegisterResult registerResult = facade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "gamename!");
        var createGameResult = facade.createGame(createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest(auth, "white", createGameResult.gameID());
        var joinGameResult = facade.joinGame(joinGameRequest);
        Assertions.assertNotNull(joinGameResult);
    }

    @Test
    public void badJoinGameTest() throws ResponseException {
        RegisterResult registerResult = facade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameResult createGameResult = facade.createGame(new CreateGameRequest(auth, "gamename!"));
        int gameID = createGameResult.gameID();
        JoinGameRequest joinGameRequest = new JoinGameRequest(auth, "white", gameID);
        facade.joinGame(joinGameRequest);
        Assertions.assertThrows(ResponseException.class, () -> facade.joinGame(joinGameRequest));
    }

    @Test
    public void listGamesTest() throws ResponseException {
        RegisterResult registerResult = facade.register(new RegisterRequest("new", "newpass", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "newgame");
        var createGameResult = facade.createGame(createGameRequest);
        CreateGameRequest createGameRequest2 = new CreateGameRequest(auth, "antohergame");
        var createGameResult2 = facade.createGame(createGameRequest);
        ListGamesRequest listGamesRequest = new ListGamesRequest(auth);
        var listGamesResult = facade.listGames(listGamesRequest);
        Assertions.assertNotNull(listGamesResult);
    }

    @Test
    public void badListGamesTest() throws ResponseException {
        RegisterResult registerResult = facade.register(new RegisterRequest("new", "newpass", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "newgame");
        var createGameResult = facade.createGame(createGameRequest);
        CreateGameRequest createGameRequest2 = new CreateGameRequest(auth, "antohergame");
        var createGameResult2 = facade.createGame(createGameRequest);
        ListGamesRequest listGamesRequest = new ListGamesRequest("auth");
        Assertions.assertThrows(ResponseException.class, () -> facade.listGames(listGamesRequest));
    }
}
