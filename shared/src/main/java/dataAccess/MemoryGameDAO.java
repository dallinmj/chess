package dataAccess;

import dataaccess.DataAccessException;
import model.GameData;

import java.util.ArrayList;
import java.util.Objects;

public class MemoryGameDAO implements GameDAO {
    private final ArrayList<GameData> games = new ArrayList<>();


    @Override
    public void createGame(GameData g) throws DataAccessException {
        games.add(g);
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        return games;
    }

    @Override
    public GameData getGame(int gameId) throws DataAccessException {
        for (GameData g : games) {
            if (g.getGameID() == gameId) {
                return g;
            }
        }
        throw new DataAccessException("Error: game not found");
    }

    @Override
    public String checkTeamColor(GameData g, String playerColor) throws DataAccessException {
        if (Objects.equals(playerColor.toUpperCase(), "WHITE")){
            return g.getWhiteUsername();
        } else if (Objects.equals(playerColor.toUpperCase(), "BLACK")) {
            return g.getBlackUsername();
        }
        throw new DataAccessException("Invalid player color");
    }

    @Override
    public void addPlayer(GameData g, String playerColor, String username) throws DataAccessException {
        GameData updatedGame;
        if (Objects.equals(playerColor.toUpperCase(), "WHITE")) {
            updatedGame = new GameData(g.getGameID(), username, g.getBlackUsername(), g.getGameName(), g.getGame());
        } else if (Objects.equals(playerColor.toUpperCase(), "BLACK")) {
            updatedGame = new GameData(g.getGameID(), g.getWhiteUsername(), username, g.getGameName(), g.getGame());
        } else {
            throw new DataAccessException("Invalid player color");
        }
        games.remove(g);
        games.add(updatedGame);
    }

    @Override
    public void deleteGame(GameData g) throws DataAccessException {

    }

    @Override
    public void clearAllGames() throws DataAccessException {
        games.clear();
    }
}
