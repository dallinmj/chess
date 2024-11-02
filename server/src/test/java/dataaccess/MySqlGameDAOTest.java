package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static dataaccess.MySqlAuthDAO.executeQuery;
import static org.junit.jupiter.api.Assertions.*;

class MySqlGameDAOTest extends BaseMySqlDAOTest {

    @Test
    void createGame() throws DataAccessException {
        GameData gameData = new GameData(123, null,
                null, "GameName", new ChessGame());
        mySqlGameDAO.createGame(gameData);
        GameData checkGameData = mySqlGameDAO.getGame(123);
        assertEquals(gameData, checkGameData);
    }

    @Test
    void listGames() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("diggidy", "pass", "emma"));
        GameData gameData1 = new GameData(1, null,
                null, "GameName", new ChessGame());
        GameData gameData2 = new GameData(2, null,
                null, "GameName", new ChessGame());
        GameData gameData3 = new GameData(3, "diggidy",
                null, "GameName", new ChessGame());
        mySqlGameDAO.createGame(gameData1);
        mySqlGameDAO.createGame(gameData2);
        mySqlGameDAO.createGame(gameData3);
        ArrayList<GameData> gameDataArrayList = new ArrayList<>(List.of(gameData1, gameData2, gameData3));
        assertEquals(gameDataArrayList, mySqlGameDAO.listGames());
    }

    @Test
    void getGame() throws DataAccessException {
        GameData gameData = new GameData(4, null,
                null, "GetMe!", new ChessGame());
        mySqlGameDAO.createGame(gameData);
        assertEquals(gameData, mySqlGameDAO.getGame(4));
    }

    @Test
    void checkTeamColor() throws DataAccessException {
        UserData userData = new UserData("getMeBro", "pass", "emma");
        mySqlUserDAO.createUser(userData);
        GameData gameData = new GameData(5, "getMeBro",
                null, "Hey bro", new ChessGame());
        mySqlGameDAO.createGame(gameData);
        System.out.println(mySqlGameDAO.checkTeamColor(gameData, "white"));
        assertEquals("getMeBro", mySqlGameDAO.checkTeamColor(gameData, "white"));
    }

    @Test
    void addPlayer() throws DataAccessException {
        UserData userData = new UserData("addMe", "hey", "ammo");
        mySqlUserDAO.createUser(userData);
        GameData gameData = new GameData(6, null,
                null, "GiveMe", new ChessGame());
        mySqlGameDAO.createGame(gameData);

        System.out.println(mySqlGameDAO.getGame(6));

        mySqlGameDAO.addPlayer(gameData, "white", "addMe");

        assertEquals(new GameData(6, "addMe", null,
                "GiveMe", new ChessGame()), mySqlGameDAO.getGame(6));
    }

    @Test
    void clearAllGames() throws DataAccessException {
        mySqlGameDAO.createGame(new GameData(7, null,
                null, "GameName", new ChessGame()));
        mySqlGameDAO.clearAllGames();
        assertEquals(new ArrayList<GameData>(), mySqlGameDAO.listGames());
    }
}