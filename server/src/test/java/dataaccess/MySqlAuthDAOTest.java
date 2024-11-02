package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySqlAuthDAOTest extends BaseMySqlDAOTest {

    @Test
    void createAuth() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("diggy", "password123", "email@123"));
        AuthData authData = new AuthData("authToken123", "diggy");
        mySqlAuthDAO.createAuth(authData);
    }

    @Test
    void getAuth() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("dallinmj", "monkey", "dal@gmail.com"));
        mySqlAuthDAO.createAuth(new AuthData("authydal", "dallinmj"));
        System.out.println(mySqlAuthDAO.getAuth("authydal"));
    }

    @Test
    void deleteAuth() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("deletemeauth", "ohno", "delete@me.com"));
        AuthData authData = new AuthData("deleteauth", "deletemeauth");

        mySqlAuthDAO.createAuth(authData);
        System.out.println(mySqlAuthDAO.getAuth(authData.authToken()));
        mySqlAuthDAO.deleteAuth(authData);
        try {
            System.out.println(mySqlAuthDAO.getAuth(authData.authToken()));
        } catch (DataAccessException e) {
            assertEquals(e.getMessage(), "Error: unauthorized");
        }
    }

    @Test
    void clearAllAuth() {
    }

    @Test
    void listAuths() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("user1", "pass1", "email1"));
        mySqlUserDAO.createUser(new UserData("user2", "pass2", "email2"));
        mySqlUserDAO.createUser(new UserData("user3", "pass3", "email3"));
        mySqlAuthDAO.createAuth(new AuthData("auth1", "user1"));
        mySqlAuthDAO.createAuth(new AuthData("auth2", "user2"));
        mySqlAuthDAO.createAuth(new AuthData("auth3", "user3"));

        System.out.println(mySqlAuthDAO.listAuths());
    }

    @Test
    void getUsername() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("getMe", "getmeplz", "emailyay"));
        mySqlAuthDAO.createAuth(new AuthData("useMe", "getMe"));
        System.out.println(mySqlAuthDAO.getUsername("useMe"));
    }
}