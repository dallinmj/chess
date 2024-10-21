// GamesResponse.java
package service.request_result;

import java.util.List;

public class GamesResponse {
    private final List<GameResponse> games;

    public GamesResponse(List<GameResponse> games) {
        this.games = games;
    }

    public List<GameResponse> getGames() {
        return games;
    }
}