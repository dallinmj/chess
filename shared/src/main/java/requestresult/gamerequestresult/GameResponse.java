package requestresult.gamerequestresult;

import model.GameData;

public class GameResponse {
    private final int gameID;
    private final String whiteUsername;
    private final String blackUsername;
    private final String gameName;

    public GameResponse(GameData gameData) {
        this.gameID = gameData.getGameID();
        this.whiteUsername = gameData.getWhiteUsername();
        this.blackUsername = gameData.getBlackUsername();
        this.gameName = gameData.getGameName();
    }
}