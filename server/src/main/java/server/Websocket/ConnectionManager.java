package server.Websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String auth, int gameId, Session session) {
        var connection = new Connection(auth, gameId, session);
        connections.put(auth, connection);
    }

    public void remove(String auth) {
        connections.remove(auth);
    }

    public void broadcast(String excludeAuth, int gameId, ServerMessage message) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.auth.equals(excludeAuth) && c.gameId == gameId) {
                    String jsonMessage = new Gson().toJson(message);
                    c.send(jsonMessage);

                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.auth);
        }
    }
}
