package dataAccess;

import dataaccess.DataAccessException;
import model.UserData;

public interface UserDAO {

    void createUser(UserData u) throws DataAccessException;

    void getUser(String username) throws DataAccessException;

    void deleteUser(UserData u) throws DataAccessException;

    void clearAllUsers() throws DataAccessException;
}
