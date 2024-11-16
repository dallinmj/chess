package requestresult.gamerequestresult;

public record CreateGameResult(int gameID) {
    public int gameId() {
        return gameID;
    }
}
