package dataAccess;

import model.UserData;

import java.util.ArrayList;

public interface UserDAO {

    void createUser(UserData u) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    void deleteUser(UserData u) throws DataAccessException;

    void clearAllUsers() throws DataAccessException;

    ArrayList<UserData> listUsers() throws DataAccessException;
}
