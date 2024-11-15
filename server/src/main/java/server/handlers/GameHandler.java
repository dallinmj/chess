package server.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccessException;
import service.GameService;
import service.requestresult.*;
import service.requestresult.gamerequestresult.*;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameHandler {
    private final GameService gameService;
    private final Gson gson = new Gson();

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public String createGame(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            String gameName = gson.fromJson(req.body(), JsonObject.class).get("gameName").getAsString();
            CreateGameRequest createGameRequest = new CreateGameRequest(authToken, gameName);

            if (createGameRequest.gameName() == null) {
                throw new DataAccessException("Error: bad request");
            }

            CreateGameResult createGameResult = gameService.createGame(createGameRequest);
            return gson.toJson(createGameResult);
        } catch(DataAccessException e) {
            if (Objects.equals(e.getMessage(), "Error: unauthorized")) {
                res.status(401);
            } else if(Objects.equals(e.getMessage(), "Error: bad request")) {
                res.status(400);
            }
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        } catch(Exception e) {
            res.status(500);
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return gson.toJson(errorResponse);
        }
    }

    public String listGames(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);
            ListGamesResult listGamesResult = gameService.listGames(listGamesRequest);

            // Map GameData to GameResponse
            List<GameResponse> gameResponses = listGamesResult.games().stream()
                    .map(GameResponse::new)
                    .collect(Collectors.toList());

            return gson.toJson(new GamesResponse(gameResponses));
        } catch(DataAccessException e) {
            res.status(401);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        } catch (Exception e) {
            res.status(500);
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage() + " " + req);
            return gson.toJson(errorResponse);
        }
    }

    public String joinGame(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            JsonObject requestBody = gson.fromJson(req.body(), JsonObject.class);
            String color;
            int gameID;
            try {
                color = requestBody.get("playerColor").getAsString();
                gameID = requestBody.get("gameID").getAsInt();
            } catch (Exception e) {
                throw new DataAccessException("Error: bad request");
            }
            JoinGameRequest joinGameRequest = new JoinGameRequest(authToken, color, gameID);
            JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);
            return gson.toJson(joinGameResult);
        } catch(DataAccessException e) {
            if (Objects.equals(e.getMessage(), "Error: bad request")) {
                res.status(400);
            } else if (Objects.equals(e.getMessage(), "Error: unauthorized")) {
                res.status(401);
            } else if (Objects.equals(e.getMessage(), "Error: already taken")) {
                res.status(403);
            }
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        } catch(Exception e){
            res.status(500);
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return gson.toJson(errorResponse);
        }
    }
}
