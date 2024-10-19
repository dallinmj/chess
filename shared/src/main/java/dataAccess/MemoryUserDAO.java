package dataAccess;

import dataaccess.DataAccessException;
import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO {
    private final ArrayList<UserData> users = new ArrayList<>();

    @Override
    public void createUser(UserData u) throws DataAccessException {
        users.add(u);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        for (UserData u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        throw new DataAccessException("User not found");
    }

    @Override
    public void deleteUser(UserData u) throws DataAccessException {
        users.remove(u);
    }

    @Override
    public void clearAllUsers() throws DataAccessException {
        users.clear();
    }
}
