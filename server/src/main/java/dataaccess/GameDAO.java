package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO {

    void createGame(GameData g) throws DataAccessException;

    ArrayList<GameData> listGames() throws DataAccessException;

    GameData getGame(int gameId) throws DataAccessException;

    String checkTeamColor(GameData g, String playerColor)  throws DataAccessException;

    void addPlayer(GameData g, String playerColor, String username) throws DataAccessException;

    void clearAllGames() throws DataAccessException;
}
