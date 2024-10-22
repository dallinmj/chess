package service.requestresult.userrequestresult;

public record RegisterRequest(String username,
                              String password,
                              String email){

    public String username() {
        return username;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }
}