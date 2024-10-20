package service;

import dataAccess.*;
import dataaccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request_result.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private ClearService clearService;
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;

    @BeforeEach
    void setUp() {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        gameDAO = new MemoryGameDAO();
        userService = new UserService(authDAO, userDAO, gameDAO);
        clearService = new ClearService(authDAO, userDAO, gameDAO);
    }

    @AfterEach
    void tearDown() {
        clearService.clear(new ClearRequest());
    }

    @Test
    void register() throws DataAccessException {
        RegisterResult registerResult = userService.register(new RegisterRequest("register", "password", "email"));
        assertEquals("register", registerResult.username());
        UserData userData = userDAO.getUser("register");
        assertEquals(new UserData("register", "password", "email"), userDAO.getUser("register"));
    }

    @Test // Already taken
    void badRegister() throws DataAccessException {
        userDAO.createUser(new UserData("register", "password", "email"));
        try {
            userService.register(new RegisterRequest("register", "password", "email"));
        } catch (DataAccessException e) {
            assertEquals("Error: already taken", e.getMessage());
        }
    }

    @Test
    void login() throws DataAccessException{
        userDAO.createUser(new UserData("login", "password", "email"));
        LoginResult loginResult = userService.login(new LoginRequest("login", "password"));
        assertEquals("login", loginResult.username());
    }

    @Test // Unauthorized
    void badLogin() throws DataAccessException {
        userDAO.createUser(new UserData("login", "password", "email"));
        try {
            userService.login(new LoginRequest("login", "bad_password"));
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
        }
    }

    @Test
    void logout() throws DataAccessException {
        userDAO.createUser(new UserData("logout", "password", "email"));
        LoginResult loginResult = userService.login(new LoginRequest("logout", "password"));
        LogoutResult logoutResult = userService.logout(new LogoutRequest(loginResult.authToken()));
        try {
            authDAO.getAuth(loginResult.authToken());
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
            assertEquals("", logoutResult.message());
        }
    }
}