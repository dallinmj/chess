package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlAuthDAOTest extends BaseMySqlDAOTest {

    @Test
    void createAuth() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("diggy", "password123", "email@123"));
        AuthData authData = new AuthData("authToken123", "diggy");
        mySqlAuthDAO.createAuth(authData);
        AuthData checkAuthData = mySqlAuthDAO.getAuth("authToken123");
        assertEquals(checkAuthData, authData);
    }

    @Test
    void getAuth() throws DataAccessException {
        AuthData authData = new AuthData("authydal", "dallinmj");
        UserData userData = new UserData("dallinmj", "monkey", "dal@gmail.com");
        mySqlUserDAO.createUser(userData);
        mySqlAuthDAO.createAuth(authData);
        AuthData checkAuthData = mySqlAuthDAO.getAuth("authydal");
        assertEquals(authData, checkAuthData);
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
    void listAuths() throws DataAccessException {
        AuthData authData1 = new AuthData("auth1", "user1");
        AuthData authData2 = new AuthData("auth2", "user2");
        AuthData authData3 = new AuthData("auth3", "user3");

        mySqlUserDAO.createUser(new UserData("user1", "pass1", "email1"));
        mySqlUserDAO.createUser(new UserData("user2", "pass2", "email2"));
        mySqlUserDAO.createUser(new UserData("user3", "pass3", "email3"));
        mySqlAuthDAO.createAuth(authData1);
        mySqlAuthDAO.createAuth(authData2);
        mySqlAuthDAO.createAuth(authData3);
        ArrayList<AuthData> authDataList = new ArrayList<>(List.of(authData1, authData2, authData3));

        ArrayList<AuthData> checkAuthDataList = mySqlAuthDAO.listAuths();
        assertEquals(authDataList, checkAuthDataList);
    }

    @Test
    void getUsername() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("getMe", "getmeplz", "emailyay"));
        mySqlAuthDAO.createAuth(new AuthData("useMe", "getMe"));
        String username = mySqlAuthDAO.getUsername("useMe");
        assertEquals("getMe", username);
    }
}