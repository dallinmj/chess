package service.request_result;

public record JoinGameResult(String result) {
    public String message() {
        return result;
    }
}
