package network;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import requestresult.gamerequestresult.CreateGameRequest;
import requestresult.gamerequestresult.JoinGameRequest;
import requestresult.gamerequestresult.ListGamesRequest;
import requestresult.userrequestresult.LogoutRequest;

import java.io.*;
import java.net.*;

public class ClientCommunicator {

    private final String serverUrl;
    private final Gson gson = new Gson();

    public ClientCommunicator(String serverURL) {
        this.serverUrl = serverURL;
    }

    <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws DataAccessException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(!method.equals("GET"));

            setAuthHeader(http, request);

            if (!method.equals("GET")) {
                writeBody(request, http);
            }
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private void setAuthHeader(HttpURLConnection http, Object request) {
        if (request instanceof LogoutRequest) {
            http.addRequestProperty("Authorization", ((LogoutRequest) request).authToken());
        } else if (request instanceof CreateGameRequest) {
            http.addRequestProperty("Authorization", ((CreateGameRequest) request).authToken());
        } else if (request instanceof JoinGameRequest) {
            http.addRequestProperty("Authorization", ((JoinGameRequest) request).authToken());
        } else if (request instanceof ListGamesRequest) {
            http.addRequestProperty("Authorization", ((ListGamesRequest) request).authToken());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws DataAccessException, IOException {
        var status = http.getResponseCode();
        var somethign = http.getResponseMessage();
        if (!isSuccessful(status)) {
            throw new DataAccessException("failure: " + status + " " + somethign);
        }
    }

    private boolean isSuccessful(int status) {
        return status >= 200 && status < 300;
    }
}
