package service.request_result;

import model.GameData;

public class GameResponse {
    private final int gameId;
    private final String whiteUsername;
    private final String blackUsername;
    private final String gameName;

    public GameResponse(GameData gameData) {
        this.gameId = gameData.getGameID();
        this.whiteUsername = gameData.getWhiteUsername();
        this.blackUsername = gameData.getBlackUsername();
        this.gameName = gameData.getGameName();
    }

    public int getGameId() {
        return gameId;
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
}