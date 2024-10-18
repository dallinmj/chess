package dataAccess;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.GameData;

public interface GameDAO {

    void createGame(GameData g) throws DataAccessException;

    void listGames() throws DataAccessException;

    void findGame() throws DataAccessException;

    void checkTeamColor(GameData g, ChessGame.TeamColor playerColor)  throws DataAccessException;

    void addPlayer(GameData g, ChessGame.TeamColor playerColor) throws DataAccessException;

    void deleteGame(GameData g) throws DataAccessException;

    void clearAllGames() throws DataAccessException;
}
