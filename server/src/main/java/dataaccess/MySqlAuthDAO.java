package dataaccess;

import model.AuthData;

import java.sql.SQLException;
import java.util.ArrayList;

public class MySqlAuthDAO implements AuthDAO {

    public MySqlAuthDAO() throws DataAccessException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.configureDatabase();
    }

    @Override
    public void createAuth(AuthData a) throws DataAccessException {
        var statement = "insert into Auth (authToken, username) values (?, ?)";
        executeUpdate(statement, ps -> {
            ps.setString(1, a.authToken());
            ps.setString(2, a.username());
        });
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        var statement = "Select username from Auth where authToken=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()){
                    if (rs.next()) {
                        String username = rs.getString("username");
                        return new AuthData(authToken, username);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteAuth(AuthData a) throws DataAccessException {
        var statement = "Delete from Auth where authToken=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, a.authToken());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clearAllAuth() throws DataAccessException {
        var statement = "Delete from Auth";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public ArrayList<AuthData> listAuths() throws DataAccessException {
        var result = new ArrayList<AuthData>();
        var statement = "Select authToken, username from Auth";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String authToken = rs.getString("authToken");
                        String username = rs.getString("username");
                        result.add(new AuthData(authToken, username));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return result;
    }

    @Override
    public String getUsername(String authToken) throws DataAccessException {
        return "";
    }

    @FunctionalInterface
    private interface StatementPreparer {
        void prepare(java.sql.PreparedStatement ps) throws SQLException;
    }

    private void executeUpdate(String statement, StatementPreparer preparer) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                preparer.prepare(ps);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}

