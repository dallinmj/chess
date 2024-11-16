package server.handlers;

import com.google.gson.Gson;
import service.ClearService;
import requestresult.ClearRequest;
import requestresult.ClearResult;
import requestresult.ErrorResponse;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private final ClearService clearService;
    private final Gson gson = new Gson();

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }

    public String clear(Request req, Response res) {
        try {
            ClearRequest clearRequest = new ClearRequest();
            ClearResult clearResult = clearService.clear(clearRequest);
            return gson.toJson(clearResult);
        } catch (Exception e) {
            res.status(500);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }
}
