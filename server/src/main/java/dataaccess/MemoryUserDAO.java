package dataaccess;

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
    public void clearAllUsers() throws DataAccessException {
        users.clear();
    }

    @Override
    public ArrayList<UserData> listUsers() throws DataAccessException {
        return users;
    }
}
