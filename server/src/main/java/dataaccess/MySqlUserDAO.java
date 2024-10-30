package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MySqlUserDAO implements UserDAO{
    @Override
    public void createUser(UserData u) throws DataAccessException {

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
