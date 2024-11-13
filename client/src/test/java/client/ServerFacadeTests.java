package client;

import dataaccess.DataAccessException;
import network.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;
import service.requestresult.ClearRequest;
import service.requestresult.gamerequestresult.CreateGameRequest;
import service.requestresult.gamerequestresult.JoinGameRequest;
import service.requestresult.gamerequestresult.ListGamesRequest;
import service.requestresult.userrequestresult.*;


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
    public void clear() throws DataAccessException {
         ServerFacade.clear(new ClearRequest());
    }


    @Test
    public void loginTest() throws DataAccessException {
        ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        LoginRequest request = new LoginRequest("doug", "goodpassword");
        var result = ServerFacade.login(request);
        Assertions.assertNotNull(result);
    }

    @Test
    public void registerTest() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("doug", "goodpassword", "Diggyemail");
        var result = ServerFacade.register(request);
        Assertions.assertNotNull(result);
    }

    @Test
    public void logoutTest() throws DataAccessException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();

        LogoutRequest logoutRequest = new LogoutRequest(auth);
        var result = ServerFacade.logout(logoutRequest);
        Assertions.assertNotNull(result);
    }

    @Test
    public void createGameTest() throws DataAccessException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "gamename!");
        var createGameResult = ServerFacade.createGame(createGameRequest);
        Assertions.assertNotNull(createGameResult);
    }

    @Test
    public void joinGameTest() throws DataAccessException {
        RegisterResult registerResult = ServerFacade.register(new RegisterRequest("doug", "goodpassword", "Diggyemail"));
        String auth = registerResult.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest(auth, "gamename!");
        var createGameResult = ServerFacade.createGame(createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest(auth, "white", createGameResult.gameID());
        var joinGameResult = ServerFacade.joinGame(joinGameRequest);
        Assertions.assertNotNull(joinGameResult);
    }

    @Test
    public void listGamesTest() throws DataAccessException {
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

}
