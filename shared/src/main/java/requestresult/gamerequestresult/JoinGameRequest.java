package requestresult.gamerequestresult;

public record JoinGameRequest(String authToken, String playerColor, int gameID) {
    public String authToken() {
        return authToken;
    }

    public String color() {
        return playerColor;
    }

    public int gameID() {
        return gameID;
    }
}
