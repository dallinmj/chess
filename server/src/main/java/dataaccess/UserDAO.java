package dataaccess;

import model.UserData;

import java.util.ArrayList;

public interface UserDAO {

    void createUser(UserData u) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    void clearAllUsers() throws DataAccessException;

    ArrayList<UserData> listUsers() throws DataAccessException;
}
