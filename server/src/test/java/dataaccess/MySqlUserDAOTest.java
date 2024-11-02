package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

class MySqlUserDAOTest extends BaseMySqlDAOTest {

    @Test
    void createUser() throws DataAccessException {
        UserData userData = new UserData("username123", "password123", "email@123");
        mySqlUserDAO.createUser(userData);
        UserData checkUserData = mySqlUserDAO.getUser("username123");
        assertTrue(BCrypt.checkpw("password123", checkUserData.password()));
    }

    @Test
    void getUser() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("username123", "password123", "email@123"));
        UserData checkUserData = mySqlUserDAO.getUser("username123");
        assertEquals("username123", checkUserData.username());
        assertTrue(BCrypt.checkpw("password123", checkUserData.password()));
        assertEquals("email@123", checkUserData.email());
    }

    @Test
    void clearAllUsers() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("username123", "password123", "email@123"));
        mySqlUserDAO.createUser(new UserData("username456", "password456", "email@456"));
        mySqlUserDAO.createUser(new UserData("username789", "password789", "email@789"));

        mySqlAuthDAO.clearAllAuth();
        mySqlUserDAO.clearAllUsers();

        assertEquals(0, mySqlUserDAO.listUsers().size());
    }

    @Test
    void listUsers() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("username123", "password123", "email@123"));
        mySqlUserDAO.createUser(new UserData("username456", "password456", "email@456"));
        mySqlUserDAO.createUser(new UserData("username789", "password789", "email@789"));

        assertEquals(3, mySqlUserDAO.listUsers().size());
    }
}