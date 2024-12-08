package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public String auth;
    public int gameId;
    public Session session;

    @Override
    public String toString() {
        return "Connection{" +
                "auth='" + auth + '\'' +
                ", gameId=" + gameId +
                '}';
    }

    public Connection(String auth, int gameId, Session session) {
        this.auth = auth;
        this.gameId = gameId;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}