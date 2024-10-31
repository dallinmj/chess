package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySqlAuthDAOTest {

    MySqlAuthDAO mySqlAuthDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        mySqlAuthDAO = new MySqlAuthDAO();
    }

    @Test
    void createAuth() throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.configureDatabase();
        AuthData authData = new AuthData("authToken123", "username123");
        mySqlAuthDAO.createAuth(authData);
    }

    @Test
    void getAuth() {
    }

    @Test
    void deleteAuth() {
    }

    @Test
    void clearAllAuth() {
    }

    @Test
    void listAuths() {
    }

    @Test
    void getUsername() {
    }
}