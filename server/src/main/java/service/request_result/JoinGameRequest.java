package service.request_result;

public record JoinGameRequest(String authToken, String color, int gameID) {
    public String authToken() {
        return authToken;
    }

    public String color() {
        return color;
    }

    public int gameID() {
        return gameID;
    }
}
