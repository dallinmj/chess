package dataAccess;

import dataaccess.DataAccessException;
import model.AuthData;

import java.util.ArrayList;

public class MemoryAuthDAO implements AuthDAO {
    private ArrayList<AuthData> auths = new ArrayList<>();

    @Override
    public void createAuth(AuthData a) throws DataAccessException {
        auths.add(a);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
//        for (AuthData a : auths) {
//            if (a.getAuthToken().equals(authToken)) {
//                return a;
//            }
//        }
//        throw new DataAccessException("Auth not found");
        return null;
    }

    @Override
    public void deleteAuth(AuthData a) throws DataAccessException {

    }

    @Override
    public void clearAllAuth() throws DataAccessException {

    }
}
