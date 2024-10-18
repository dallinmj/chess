package dataAccess;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.GameData;

public class MemoryGameDAO implements GameDAO {


    @Override
    public void createGame(GameData g) throws DataAccessException {

    }

    @Override
    public void listGames() throws DataAccessException {

    }

    @Override
    public void findGame() throws DataAccessException {

    }

    @Override
    public void checkTeamColor(GameData g, ChessGame.TeamColor playerColor) throws DataAccessException {

    }

    @Override
    public void addPlayer(GameData g, ChessGame.TeamColor playerColor) throws DataAccessException {

    }

    @Override
    public void deleteGame(GameData g) throws DataAccessException {

    }

    @Override
    public void clearAllGames() throws DataAccessException {

    }
}
