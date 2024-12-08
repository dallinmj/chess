package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDAO {

    void createGame(GameData g) throws DataAccessException;

    void updateGame(GameData g, ChessGame game) throws DataAccessException;

    ArrayList<GameData> listGames() throws DataAccessException;

    GameData getGame(int gameId) throws DataAccessException;

    String checkTeamColor(GameData g, String playerColor)  throws DataAccessException;

    void addPlayer(GameData g, String playerColor, String username) throws DataAccessException;

    void removePlayer(GameData g, String color) throws DataAccessException;

    void clearAllGames() throws DataAccessException;
}
