package dataaccess;

import model.GameData;

import java.util.ArrayList;

public class MySqlGameDAO implements GameDAO{
    @Override
    public void createGame(GameData g) throws DataAccessException {

    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public GameData getGame(int gameId) throws DataAccessException {
        return null;
    }

    @Override
    public String checkTeamColor(GameData g, String playerColor) throws DataAccessException {
        return "";
    }

    @Override
    public void addPlayer(GameData g, String playerColor, String username) throws DataAccessException {

    }

    @Override
    public void clearAllGames() throws DataAccessException {

    }
}
