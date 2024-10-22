package service;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataaccess.DataAccessException;
import model.GameData;
import service.request_result.*;

import java.util.Random;

public class GameService {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws DataAccessException {
        authDAO.getAuth(listGamesRequest.authToken());
        return new ListGamesResult(gameDAO.listGames());
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        authDAO.getAuth(createGameRequest.authToken());
        int gameID = new Random().nextInt(1000);
        gameDAO.createGame(new GameData(gameID, null, null,
                createGameRequest.gameName(), new ChessGame()));
        return new CreateGameResult(gameID);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws DataAccessException {
        authDAO.getAuth(joinGameRequest.authToken());
        String username = authDAO.getUsername(joinGameRequest.authToken());
        GameData gameData = gameDAO.getGame(joinGameRequest.gameID());
        String player = gameDAO.checkTeamColor(gameData, joinGameRequest.color());
        if (player == null) {
            gameDAO.addPlayer(gameData, joinGameRequest.color(), username);
            return new JoinGameResult();
        }
        throw new DataAccessException("Error: already taken");
    }
}
