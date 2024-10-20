package service.request_result;

public record LogoutResult(String message){
    public String message() {
        return message;
    }
}