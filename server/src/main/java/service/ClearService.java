package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import dataaccess.DataAccessException;
import service.request_result.ClearRequest;
import service.request_result.ClearResult;

public class ClearService {

    private final AuthDAO authDAO;
    private final UserDAO userDAO;
    private final GameDAO gameDAO;

    public ClearService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
        this.gameDAO = gameDAO;
    }

    public ClearResult clear(ClearRequest request) {
        try{
            authDAO.clearAllAuth();
            userDAO.clearAllUsers();
            gameDAO.clearAllGames();
            return new ClearResult("");
        } catch (DataAccessException e) {
            return new ClearResult(e.getMessage());
        }
    }
}
