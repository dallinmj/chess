package websocket.messages;

public class NotificationMessage extends ServerMessage {

    private final String message;

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "message='" + message + '\'' +
                '}';
    }

    public NotificationMessage(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
