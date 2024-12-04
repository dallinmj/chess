package network;

import chess.ChessMove;
import com.google.gson.Gson;
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

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    serverMessageObserver.notify(serverMessage);
                }
            });
        } catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void connect(String auth, int gameId) throws ResponseException {
        try {
            var action = UserGameCommand.connect(auth, gameId);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void makeMove(ChessMove move) throws ResponseException {
        try {
            var action = new MakeMoveCommand(move);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void leave(String auth, int gameId) throws ResponseException {
        try {
            var action = UserGameCommand.leave(auth, gameId);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
            this.session.close();
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void resign(String auth, int gameId) throws ResponseException {
        try {
            var action = UserGameCommand.resign(auth, gameId);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    public void loadGame(String game) throws ResponseException {
        try {
            var message = new LoadGameMessage(game);
            sendMessage(message);
        } catch (ResponseException e) {
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
