package websocket.messages;

public class ErrorMessage {

    private final String errorMessage;

    public ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
