package model;

public record AuthData(String authToken, String username) {
    public Object getAuthToken() {
        return authToken;
    }
}
