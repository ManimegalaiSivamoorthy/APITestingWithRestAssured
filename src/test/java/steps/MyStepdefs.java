package steps;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.it.Ma;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import utils.RestAssuredExtension;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MyStepdefs {

    public static ResponseOptions<Response> response;

    ObjectMapper objectMapper = new ObjectMapper();

    HashMap<String, String> requestBody = new HashMap<>();

    RestAssuredExtension restAssuredExtension = new RestAssuredExtension();

    @When("Perform GET operation for the endpoint {string}")
    public void performGETOperationForTheEndpoint(String url) {
        response = restAssuredExtension.getOps(url);
    }

    @When("Perform GET operation for the endpoint {string} with query parameter {string} and value {string}")
    public void performGETOperationForTheEndpointWithQueryParameterAndValue(String url, String queryKey, String queryValue) {
        Map<String, String> queryParam = new HashMap<String, String>();
        queryParam.put(queryKey, queryValue);
        response = restAssuredExtension.getOpsWithQueryParameter(url, queryParam);
    }

    @Then("The status code should be {int}")
    public void theStatusCodeShouldBe(Integer statusCode) {
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }

    @And("The response body should contain the key {string} and value {string}")
    public void theResponseBodyShouldContainTheKeyAndValue(String queryKey, String queryValue) throws IOException {
        String responseAsString = response.getBody().asString();
        model.Response apiResponse = objectMapper.readValue(responseAsString, model.Response.class);
        assertThat(apiResponse.getArgs().get(queryKey), equalTo(queryValue));
    }

    @Then("output status code must match the input {int}")
    public void outputStatusCodeMustMatchTheInput(Integer inputStatusCode) {
        assertThat(response.getStatusCode(), equalTo(inputStatusCode));
    }

    @Then("Should get a simple json as response")
    public void shouldGetASimpleJsonAsResponse() throws JSONException {
        String expectedJson = "";
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/TestJson/getEndpoint.json");
            expectedJson = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
       // JSONAssert.assertEquals(EXPECTED_RESULT_FOR_JSON_EP, response.getBody().asString(),true);
        JSONAssert.assertEquals(expectedJson, response.getBody().asString(),true);
    }

    @Given("The request body for the POST operation")
    public void theRequestBodyForThePOSTOperation(DataTable table) {
        List<Map<String, String>> data = table.asMaps(String.class, String.class);
        requestBody.put("name", data.get(0).get("name"));
        requestBody.put("age", data.get(0).get("age"));
        requestBody.put("profession", data.get(0).get("profession"));
    }

    @When("Perform the Post operation with {string}")
    public void performThePostOperationWith(String url) {
        response = restAssuredExtension.postOpsWithBody(url, requestBody);
    }

    @Then("The response body should have key {string} and value {string}")
    public void theResponseBodyShouldHaveKeyAndValue(String key, String value) throws IOException {
        String responseBody = response.getBody().asString();
        model.Response postResponse = objectMapper.readValue(responseBody, model.Response.class);
        assertThat(postResponse.getJson().get(key), equalTo(value));
    }

    @Given("The request body for the POST operation from a file path {string}")
    public void theRequestBodyForThePOSTOperationFromAFilePath(String arg0) {

    }
}
