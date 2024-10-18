package dataAccess;

import dataaccess.DataAccessException;
import model.AuthData;

public interface AuthDAO {

    void createAuth(AuthData a) throws DataAccessException;

//    void getAuth(AuthData a) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(AuthData a) throws DataAccessException;

    void clearAllAuth() throws DataAccessException;
}
