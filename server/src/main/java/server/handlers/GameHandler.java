package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.GameService;
import service.request_result.*;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class GameHandler {
    private final GameService gameService;
    private final Gson gson = new Gson();

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public String createGame(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            String gameName = req.body();
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
            return gson.toJson(listGamesResult);
        } catch(DataAccessException e) {
            res.status(401);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        } catch (Exception e) {
            res.status(500);
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return gson.toJson(errorResponse);
        }
    }
}
