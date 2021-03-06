package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class RestAssuredExtension {
    public static RequestSpecification Request;

    public RestAssuredExtension() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://httpbin.org/");
        requestSpecBuilder.setContentType(ContentType.JSON);
        var requestSpec = requestSpecBuilder.build();
        Request = RestAssured.given().spec(requestSpec);
    }

    public ResponseOptions<Response> getOps(String url) {
        try {
            return Request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseOptions<Response> getOpsWithQueryParameter(String url, Map<String, String> queryParam) {
        Request.queryParams(queryParam);
        return Request.get(url);
    }

    public ResponseOptions<Response> postOpsWithBody(String url, HashMap<String, String> requestBody) {
        Request.body(requestBody);
        return Request.post(url);
    }

    public ResponseOptions<Response> postOpsJsonFromFile(String url, String jsonBody) {
        Request.body(jsonBody);
        return Request.post(url);
    }
}
