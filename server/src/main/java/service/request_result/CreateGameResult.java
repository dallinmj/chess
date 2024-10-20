package service.request_result;

public record CreateGameResult(int gameID) {
    public int gameId() {
        return gameID;
    }
}
