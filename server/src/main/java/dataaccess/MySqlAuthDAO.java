package dataaccess;

import com.google.gson.Gson;
import model.AuthData;

import java.sql.SQLException;
import java.util.ArrayList;

public class MySqlAuthDAO implements AuthDAO {

    public MySqlAuthDAO() throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.configureDatabase();
    }
    @Override
    public void createAuth(AuthData a) throws DataAccessException, SQLException {
        var statement = "Insert into Auth (authToken, username) values (?, ?)";
        try (var conn = DatabaseManager.getConnection()) {

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
