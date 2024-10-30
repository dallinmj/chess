package dataaccess;

import model.AuthData;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AuthDAO {

    void createAuth(AuthData a) throws DataAccessException, SQLException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(AuthData a) throws DataAccessException;

    void clearAllAuth() throws DataAccessException;

    ArrayList<AuthData> listAuths() throws DataAccessException;

    String getUsername(String authToken) throws DataAccessException;
}
