// GamesResponse.java
package service.requestresult.gamerequestresult;

import java.util.List;

public record GamesResponse(List<GameResponse> games) {
}