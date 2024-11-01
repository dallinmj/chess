package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.util.ArrayList;

import static dataaccess.MySqlAuthDAO.executeQuery;
import static dataaccess.MySqlAuthDAO.executeUpdate;

public class MySqlGameDAO implements GameDAO{

    public MySqlGameDAO() throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.configureDatabase();
    }

    @Override
    public void createGame(GameData g) throws DataAccessException {
        var statement = "insert into Game (gameID, whiteUsername, blackUsername, " +
                "gameName, game) values (?, ?, ?, ?, ?)";
        var serializer = new Gson();
        var json = serializer.toJson(g.game());
        executeUpdate(statement, ps -> {
            ps.setInt(1, g.gameID());
            ps.setString(2, g.whiteUsername());
            ps.setString(3, g.blackUsername());
            ps.setString(4, g.gameName());
            ps.setString(5, json);
        });
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        var serializer = new Gson();
        var result = new ArrayList<GameData>();
        var statement = "select * from Game";
        return executeQuery(statement, ps -> {}, rs -> {
            while (rs.next()) {
                int gameID = rs.getInt("gameID");
                String whiteUsername = rs.getString("whiteUsername");
                String blackUsername = rs.getString("blackUsername");
                String gameName = rs.getString("gameName");
                String gameJson = rs.getString("game");
                var chessGame = serializer.fromJson(gameJson, ChessGame.class);
                GameData game = new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
                result.add(game);
            }
            return result;
        });
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
        var statement = "delete from Game";
        executeUpdate(statement, ps -> {});
    }
}
