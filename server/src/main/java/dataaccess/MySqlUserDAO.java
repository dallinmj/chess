package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

import static dataaccess.MySqlAuthDAO.executeQuery;
import static dataaccess.MySqlAuthDAO.executeUpdate;

public class MySqlUserDAO implements UserDAO{

    @Override
    public void createUser(UserData u) throws DataAccessException {
        var statement = "insert into User (username, password, email) values (?, ?, ?)";
        executeUpdate(statement, ps -> {
            ps.setString(1, u.username());
            ps.setString(2, BCrypt.hashpw(u.password(), BCrypt.gensalt()));
            ps.setString(3, u.email());
        });
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        String statement = "select password, email from User where username=?";
        return executeQuery(statement, ps -> {
            ps.setString(1, username);
        }, rs -> {
            if (rs.next()) {
                String password = rs.getString("password");
                String email = rs.getString("email");
                return new UserData(username, password, email);
            }
            throw new DataAccessException("User not found");
        });
    }

    @Override
    public void clearAllUsers() throws DataAccessException {
        var statement = "delete from User";
        executeUpdate(statement, ps -> {});
    }

    @Override
    public ArrayList<UserData> listUsers() throws DataAccessException {
        var statement = "select username, password, email from User";
        var result = new ArrayList<UserData>();
        return executeQuery(statement, ps -> {}, rs -> {
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                result.add(new UserData(username, password, email));
            }
            return result;
        });
    }
}
