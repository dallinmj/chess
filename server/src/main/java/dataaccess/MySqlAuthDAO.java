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
        return executeQuery(statement, ps -> {
            ps.setString(1, authToken);
        }, rs -> {
            if (rs.next()) {
                String username = rs.getString("username");
                return new AuthData(authToken, username);
            }
            return null;
        });
    }

    @Override
    public void deleteAuth(AuthData a) throws DataAccessException {
        var statement = "delete from Auth where authToken=?";
        executeUpdate(statement, ps -> {
            ps.setString(1, a.authToken());
        });
    }

    @Override
    public void clearAllAuth() throws DataAccessException {
        var statement = "delete from Auth";
        executeUpdate(statement, ps -> {});
    }

    @Override
    public ArrayList<AuthData> listAuths() throws DataAccessException {
        var result = new ArrayList<AuthData>();
        var statement = "select authToken, username from Auth";
        return executeQuery(statement, ps -> {}, rs -> {
            while (rs.next()) {
                String authToken = rs.getString("authToken");
                String username = rs.getString("username");
                result.add(new AuthData(authToken, username));
            }
            return result;
        });
    }

    @Override
    public String getUsername(String authToken) throws DataAccessException {
        var statement = "select username from Auth where authToken=?";
        return executeQuery(statement, ps -> {
            ps.setString(1, authToken);
        }, rs -> {
            if (rs.next()) {
                return rs.getString("username");
            }
            return null;
        });
    }

    @FunctionalInterface
    interface StatementPreparer {
        void prepare(java.sql.PreparedStatement ps) throws SQLException;
    }

    static void executeUpdate(String statement, StatementPreparer preparer) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                preparer.prepare(ps);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @FunctionalInterface
    interface ResultSetHandler<T> {
        T handle(java.sql.ResultSet rs) throws SQLException;
    }

    static <T> T executeQuery(String statement, StatementPreparer preparer, ResultSetHandler<T> handler) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                preparer.prepare(ps);
                try (var rs = ps.executeQuery()) {
                    return handler.handle(rs);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}

