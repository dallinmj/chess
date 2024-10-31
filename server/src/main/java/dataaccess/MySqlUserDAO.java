package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MySqlUserDAO implements UserDAO{
    @Override
    public void createUser(UserData u) throws DataAccessException {
        var statement = "Insert into User (username, password, email) values (?, ?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, u.username());
                ps.setString(2, u.password());
                ps.setString(2, u.email());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void clearAllUsers() throws DataAccessException {

    }

    @Override
    public ArrayList<UserData> listUsers() throws DataAccessException {
        return null;
    }
}
