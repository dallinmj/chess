package network;

import com.google.gson.Gson;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

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
            this.session.close();
        } catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }

}
