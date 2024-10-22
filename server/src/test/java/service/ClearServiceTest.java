package service;

import chess.ChessGame;
import dataAccess.*;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.request_result.ClearRequest;
import service.request_result.ClearResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearServiceTest {

    private ClearService clearService;
    private AuthDAO authDAO;
    private UserDAO userDAO;
    private GameDAO gameDAO;

    @Test
    public void clearServiceTest() throws DataAccessException {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        gameDAO = new MemoryGameDAO();

        authDAO.createAuth(new AuthData("authToken", "username"));
        userDAO.createUser(new UserData("username", "password", "email"));
        gameDAO.createGame(new GameData(1234, "username", "username", "gameName", new ChessGame()));

        ClearService clearService = new ClearService(authDAO, userDAO, gameDAO);

        ClearResult clearResult = clearService.clear(new ClearRequest());

        assertEquals(0, authDAO.listAuths().size());
        assertEquals(0, userDAO.listUsers().size());
        assertEquals(0, gameDAO.listGames().size());
    }
}
