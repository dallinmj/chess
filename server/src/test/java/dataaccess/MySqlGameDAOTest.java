package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static dataaccess.MySqlAuthDAO.executeQuery;
import static org.junit.jupiter.api.Assertions.*;

class MySqlGameDAOTest {

    MySqlUserDAO mySqlUserDAO;
    MySqlAuthDAO mySqlAuthDAO;
    MySqlGameDAO mySqlGameDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        mySqlUserDAO = new MySqlUserDAO();
        mySqlAuthDAO = new MySqlAuthDAO();
        mySqlGameDAO = new MySqlGameDAO();
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.configureDatabase();
        mySqlAuthDAO.clearAllAuth();
        mySqlUserDAO.clearAllUsers();
        mySqlGameDAO.clearAllGames();
    }

    @Test
    void createGame() throws DataAccessException {
        GameData gameData = new GameData(123, null,
                null, "GameName", new ChessGame());
        mySqlGameDAO.createGame(gameData);
    }

    @Test
    void listGames() throws DataAccessException {

    }

    @Test
    void getGame() {
    }

    @Test
    void checkTeamColor() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void clearAllGames() throws DataAccessException {
        mySqlGameDAO.clearAllGames();
    }
}