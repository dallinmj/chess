package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import dataaccess.DataAccessException;
import service.requestresult.ClearRequest;
import service.requestresult.ClearResult;

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
            gameDAO.clearAllGames();
            authDAO.clearAllAuth();
            userDAO.clearAllUsers();
            return new ClearResult();
        } catch (DataAccessException e) {
            return new ClearResult();
        }
    }
}
