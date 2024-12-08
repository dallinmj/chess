package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.util.ArrayList;

import static dataaccess.MySqlAuthDAO.executeQuery;
import static dataaccess.MySqlAuthDAO.executeUpdate;

public class MySqlGameDAO implements GameDAO{

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
        var serializer = new Gson();
        var statement = "select * from Game where gameID=?";
        return executeQuery(statement, ps -> {
            ps.setInt(1, gameId);
        }, rs -> {
            if (rs.next()) {
                String whiteUsername = rs.getString("whiteUsername");
                String blackUsername = rs.getString("blackUsername");
                String gameName = rs.getString("gameName");
                String gameJson = rs.getString("game");
                var chessGame = serializer.fromJson(gameJson, ChessGame.class);
                return new GameData(gameId, whiteUsername, blackUsername, gameName, chessGame);
            }
            throw new DataAccessException("Error: game not found");
        });
    }

    @Override
    public String checkTeamColor(GameData g, String playerColor) throws DataAccessException {
        int gameID = g.gameID();
        String playerColorLower = playerColor.toLowerCase();

        if (!playerColorLower.equals("white") && !playerColorLower.equals("black")) {
            throw new DataAccessException("Invalid player color");
        }

        String statement = "select " + playerColorLower + "Username from Game where gameID=?";
        return executeQuery(statement, ps -> {
            ps.setInt(1, gameID);
        }, rs -> {
            if (rs.next()) {
                return rs.getString(playerColorLower + "Username");
            }
            throw new DataAccessException("checkTeamColor- Game not found or something");
        });
    }

    @Override
    public void addPlayer(GameData g, String playerColor, String username) throws DataAccessException {
        String playerColorLower = playerColor.toLowerCase();

        if (!playerColorLower.equals("white") && !playerColorLower.equals("black")) {
            throw new DataAccessException("Invalid player color");
        }

        String statement = "update Game set " + playerColorLower + "Username=? where gameID=?";
        executeUpdate(statement, ps -> {
            ps.setString(1, username);
            ps.setInt(2, g.gameID());
        });
    }

    @Override
    public void removePlayer(GameData g, String color) throws DataAccessException {
        if (color == null) {
            color = "white";
        }
        String colorLower = color.toLowerCase();
        String statement = "update Game set " + colorLower + "Username=null where gameID=?";
        executeUpdate(statement, ps -> {
            ps.setInt(1, g.gameID());
        });
    }

    public void updateGame(GameData g, ChessGame game) throws DataAccessException {
        var statement = "update Game set game=? where gameID=?";
        var serializer = new Gson();
        var json = serializer.toJson(game);
        executeUpdate(statement, ps -> {
            ps.setString(1, json);
            ps.setInt(2, g.gameID());
        });
    }

    @Override
    public void clearAllGames() throws DataAccessException {
        var statement = "delete from Game";
        executeUpdate(statement, ps -> {});
    }
}
