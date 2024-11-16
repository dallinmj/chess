package service;

import chess.ChessGame;
import dataaccess.*;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.ClearRequest;
import requestresult.gamerequestresult.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private ClearService clearService;
    private GameService gameService;
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        userDAO = new MemoryUserDAO();
        gameService = new GameService(authDAO, gameDAO);
        clearService = new ClearService(authDAO, userDAO, gameDAO);
    }

    @AfterEach
    void tearDown() {
        clearService.clear(new ClearRequest());
    }

    @Test
    void listGames() throws DataAccessException {
        authDAO.createAuth(new AuthData("authToken", "username"));
        gameDAO.createGame(new GameData(123, "player1",
                "player2", "game1", new ChessGame()));
        gameDAO.createGame(new GameData(456, "player3",
                "player4", "game2", new ChessGame()));
        ListGamesResult listGamesResult = gameService.listGames(new ListGamesRequest("authToken"));
        assertEquals(2, listGamesResult.games().size());
    }

    @Test
    void badListGames() throws DataAccessException {
        try {
            gameService.listGames(new ListGamesRequest("diggie"));
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
        }
    }

    @Test
    void createGame() throws DataAccessException {
        authDAO.createAuth(new AuthData("authToken", "username"));
        CreateGameResult createGameResult = gameService.createGame(new CreateGameRequest("authToken", "gameName"));
        GameData gameData = gameDAO.getGame(createGameResult.gameId());
        assertEquals("gameName", gameData.gameName());
    }

    @Test
    void badCreateGame() throws DataAccessException {
        try {
            authDAO.createAuth(new AuthData("authToken", "username"));
            gameService.createGame(new CreateGameRequest("authToken", null));
        } catch (DataAccessException e) {
            assertEquals("Error: bad request", e.getMessage());
        }
    }

    @Test
    void joinGame() throws DataAccessException {
        authDAO.createAuth(new AuthData("authToken", "username"));
        gameDAO.createGame(new GameData(123, null, "player", "game1", new ChessGame()));
        gameService.joinGame(new JoinGameRequest("authToken", "white", 123));
        assertEquals("username", gameDAO.getGame(123).getWhiteUsername());
    }

    @Test
    void badJoinGame() throws DataAccessException {
        try {
            authDAO.createAuth(new AuthData("authToken", "username"));
            gameDAO.createGame(new GameData(123, "Player", "Player", "diggy game", new ChessGame()));
            gameService.joinGame(new JoinGameRequest("authToken", "WHITE", 123));
        } catch(DataAccessException e) {
            assertEquals("Error: already taken", e.getMessage());
        }
    }
}