package dataAccess;

import dataaccess.DataAccessException;
import model.AuthData;

import java.util.ArrayList;

public class MemoryAuthDAO implements AuthDAO {
    private final ArrayList<AuthData> auths = new ArrayList<>();

    @Override
    public void createAuth(AuthData a) throws DataAccessException {
        auths.add(a);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        for (AuthData a : auths) {
            if (a.getAuthToken().equals(authToken)) {
                return a;
            }
        }
        throw new DataAccessException("Error: unauthorized");
    }

    @Override
    public void deleteAuth(AuthData a) throws DataAccessException {
        getAuth(a.authToken()); // throws exception if not found
        auths.remove(a);
    }

    @Override
    public void clearAllAuth() throws DataAccessException {
        auths.clear();
    }

    @Override
    public ArrayList<AuthData> listAuths() throws DataAccessException {
        return auths;
    }

    @Override
    public String getUsername(String authToken) throws DataAccessException {
        AuthData a = getAuth(authToken);
        return a.getUsername();
    }
}
