package dataaccess;

import org.junit.jupiter.api.BeforeEach;

public abstract class BaseMySqlDAOTest {

    protected MySqlAuthDAO mySqlAuthDAO;
    protected MySqlUserDAO mySqlUserDAO;
    protected MySqlGameDAO mySqlGameDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        mySqlAuthDAO = new MySqlAuthDAO();
        mySqlUserDAO = new MySqlUserDAO();
        mySqlGameDAO = new MySqlGameDAO();
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.configureDatabase();
        mySqlGameDAO.clearAllGames();
        mySqlAuthDAO.clearAllAuth();
        mySqlUserDAO.clearAllUsers();
    }
}