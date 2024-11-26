package ui;

public class ClientMain {
    public static void main(String[] args) {
        String endpoint = "http://localhost:8080";
        if (args.length == 1) {
            endpoint = args[0];
        }
        new Repl(endpoint).run();
    }
}
