package service.request_result;

public class GameSummary {
    private final int gameId;
    private final String whiteUsername;
    private final String blackUsername;
    private final String gameName;

    public GameSummary(int gameId, String whiteUsername, String blackUsername, String gameName) {
        this.gameId = gameId;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public int getGameId() {
        return gameId;
    }

}
