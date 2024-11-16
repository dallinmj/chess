package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import requestresult.userrequestresult.*;

import java.util.UUID;

public class UserService {

    private final AuthDAO authDAO;
    private final UserDAO userDAO;
    private final GameDAO gameDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
        this.gameDAO = gameDAO;
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        String username = request.username();
        String password = request.password();
        String email = request.email();
        UserData user = null;
        try {
            user = userDAO.getUser(username);
        } catch (DataAccessException ignored) {}

        if (user != null) {
            throw new DataAccessException("Error: already taken");
        }

        userDAO.createUser(new UserData(username, password, email));
        String newAuthToken = UUID.randomUUID().toString();
        authDAO.createAuth(new AuthData(newAuthToken, username));

        return new RegisterResult(username, newAuthToken);
    }
    public LoginResult login(LoginRequest request) throws DataAccessException {
        String username = request.username();
        String password = request.password();

        UserData userdata = userDAO.getUser(username);
        if (!BCrypt.checkpw(password, userdata.getPassword())) {
            throw new DataAccessException("Error: unauthorized");
        }
        String newAuthToken = UUID.randomUUID().toString();
        authDAO.createAuth(new AuthData(newAuthToken, username));
        return new LoginResult(username, newAuthToken);
    }
    public LogoutResult logout(LogoutRequest request) throws DataAccessException {
        String authToken = request.authToken();
        AuthData authData = authDAO.getAuth(authToken);
        authDAO.deleteAuth(authData);
        return new LogoutResult();
    }
}