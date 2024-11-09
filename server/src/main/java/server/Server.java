package server;

import dataaccess.*;
import server.handlers.ClearHandler;
import server.handlers.GameHandler;
import server.handlers.UserHandler;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.*;

// Unicode characters- use the Em Space character instead\
// chessboard class for gui, then client class that calls gui- two classes (then serverFacade class)
// client has main method- it calls everything
// public class ServerFacade {
//  LoginResponse login(LoginRequest request)
//  (each endpoint)

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.configureDatabase();
        } catch (DataAccessException ignored){

        }

        GameDAO gameDAO;
        AuthDAO authDAO;
        UserDAO userDAO;

        boolean sql = true;
        if (sql) {
            gameDAO = new MySqlGameDAO();
            authDAO = new MySqlAuthDAO();
            userDAO = new MySqlUserDAO();
        } else {
            gameDAO = new MemoryGameDAO();
            authDAO = new MemoryAuthDAO();
            userDAO = new MemoryUserDAO();
        }

        UserService userService = new UserService(authDAO, userDAO, gameDAO);
        GameService gameService = new GameService(authDAO, gameDAO);
        ClearService clearService = new ClearService(authDAO, userDAO, gameDAO);

        UserHandler userHandler = new UserHandler(userService);
        GameHandler gameHandler = new GameHandler(gameService);
        ClearHandler clearHandler = new ClearHandler(clearService);

        Spark.post("/user", userHandler::register);
        Spark.post("/session", userHandler::login);
        Spark.delete("/session", userHandler::logout);
        Spark.post("/game", gameHandler::createGame);
        Spark.get("/game", gameHandler::listGames);
        Spark.put("/game", gameHandler::joinGame);
        Spark.delete("/db", clearHandler::clear);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
