package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.UserService;
import service.request_result.*;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class UserHandler {
    private final UserService userService;
    private final Gson gson = new Gson();

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public String register(Request req, Response res) {
        try {
            RegisterRequest registerRequest = gson.fromJson(req.body(), RegisterRequest.class);

            if (registerRequest.username() == null | registerRequest.password() == null | registerRequest.email() == null) {
                throw new DataAccessException("Error: bad request");
            }

            RegisterResult registerResult = userService.register(registerRequest);
            return gson.toJson(registerResult);
        } catch (DataAccessException e) {
            if (Objects.equals(e.getMessage(), "Error: already taken")) {
                res.status(403);
            } else if (Objects.equals(e.getMessage(), "Error: bad request")) {
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
    public String login(Request req, Response res) {
        try {
            LoginRequest loginRequest = gson.fromJson(req.body(), LoginRequest.class);
            LoginResult loginResult = userService.login(loginRequest);
            return gson.toJson(loginResult);
        } catch (DataAccessException e) {
            res.status(401);
            ErrorResponse errorResponse = new ErrorResponse("Error: unauthorized");
            return gson.toJson(errorResponse);
        } catch(Exception e) {
            res.status(500);
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return gson.toJson(errorResponse);
        }
    }

    public String logout(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            LogoutRequest logoutRequest = new LogoutRequest(authToken);
            LogoutResult logoutResult = userService.logout(logoutRequest);
            return gson.toJson(logoutResult);
        } catch (DataAccessException e) {
            res.status(401);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        } catch(Exception e) {
            res.status(500);
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return gson.toJson(errorResponse);
        }
    }
}
