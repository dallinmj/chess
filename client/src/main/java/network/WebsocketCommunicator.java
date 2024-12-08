package network;

import chess.ChessMove;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.net.URI;
import javax.websocket.*;

public class WebsocketCommunicator extends Endpoint {

    Session session;
    ServerMessageObserver serverMessageObserver;

    public WebsocketCommunicator(String url, ServerMessageObserver serverMessageObserver) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.serverMessageObserver = serverMessageObserver;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(String.class, message -> {
                // Parse the JSON to figure out which message type we got
                JsonObject jsonObj = JsonParser.parseString(message).getAsJsonObject();
                ServerMessage.ServerMessageType type = ServerMessage.ServerMessageType.valueOf(
                        jsonObj.get("serverMessageType").getAsString()
                );

                ServerMessage serverMessage;
                switch(type) {
                    case NOTIFICATION:
                        serverMessage = new Gson().fromJson(message, NotificationMessage.class);
                        break;
                    case LOAD_GAME:
                        serverMessage = new Gson().fromJson(message, LoadGameMessage.class);
                        break;
                    case ERROR:
                        serverMessage = new Gson().fromJson(message, ErrorMessage.class);
                        break;
                    default:
                        serverMessage = new Gson().fromJson(message, ServerMessage.class);
                        break;
                }

                serverMessageObserver.notify(serverMessage);
            });
        } catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void connect(String auth, int gameId, String color) throws ResponseException {
        try {
            var action = UserGameCommand.connect(auth, gameId, color);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void makeMove(String auth, int gameId, ChessMove move) throws ResponseException {
        try {
            var action = new MakeMoveCommand(auth, gameId, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void leave(String auth, int gameId, String color) throws ResponseException {
        try {
            var action = UserGameCommand.leave(auth, gameId, color);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
            this.session.close();
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void resign(String auth, int gameId, String color) throws ResponseException {
        try {
            var action = UserGameCommand.resign(auth, gameId, color);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void sendNotification(String notification) throws ResponseException {
        try {
            var message = new NotificationMessage(notification);
            sendMessage(message);
        } catch (ResponseException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void sendError(String error) throws ResponseException {
        try {
            var message = new ErrorMessage(error);
            sendMessage(message);
        } catch (ResponseException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void sendMessage(ServerMessage message) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(message));
        } catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }

}
