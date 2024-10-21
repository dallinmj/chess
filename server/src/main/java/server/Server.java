package server;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import server.handlers.GameHandler;
import server.handlers.UserHandler;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        MemoryUserDAO memoryUserDAO = new MemoryUserDAO();

        // Register your endpoints and handle exceptions here.
        UserService userService = new UserService(memoryAuthDAO, memoryUserDAO, memoryGameDAO);
        GameService gameService = new GameService(memoryAuthDAO, memoryUserDAO, memoryGameDAO);

        UserHandler userHandler = new UserHandler(userService);
        GameHandler gameHandler = new GameHandler(gameService);

        Spark.post("/user", userHandler::register);
        Spark.post("/session", userHandler::login);
        Spark.delete("/session", userHandler::logout);
        Spark.post("/game", gameHandler::createGame);
        Spark.get("/game", gameHandler::listGames);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
