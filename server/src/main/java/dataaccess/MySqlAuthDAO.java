package dataaccess;

import model.AuthData;

import java.util.ArrayList;

public class MySqlAuthDAO implements AuthDAO {

    public MySqlAuthDAO() throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.configureDatabase();
    }
    @Override
    public void createAuth(AuthData a) throws DataAccessException {
        var statement = "Insert into Auth (authToken, username) values (?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, a.authToken());
                ps.setString(2, a.username());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(AuthData a) throws DataAccessException {

    }

    @Override
    public void clearAllAuth() throws DataAccessException {

    }

    @Override
    public ArrayList<AuthData> listAuths() throws DataAccessException {
        return null;
    }

    @Override
    public String getUsername(String authToken) throws DataAccessException {
        return "";
    }
}
