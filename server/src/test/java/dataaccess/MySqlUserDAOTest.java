package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySqlUserDAOTest extends BaseMySqlDAOTest {

    @Test
    void createUser() throws DataAccessException {
        UserData userData = new UserData("username123", "password123", "email@123");
        mySqlUserDAO.createUser(userData);
    }

    @Test
    void getUser() {
    }

    @Test
    void clearAllUsers() throws DataAccessException {
        // Put stuff inside
        mySqlAuthDAO.clearAllAuth();
        mySqlUserDAO.clearAllUsers();
        // Check empty
    }

    @Test
    void listUsers() {
    }
}