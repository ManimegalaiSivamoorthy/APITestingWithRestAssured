package steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import constants.TestConstants;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.internal.path.json.JSONAssertion;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import utils.RestAssuredExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static constants.TestConstants.EXPECTED_RESULT_FOR_JSON_EP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MyStepdefs {

    public static ResponseOptions<Response> response;

    ObjectMapper objectMapper = new ObjectMapper();

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

        JSONAssert.assertEquals(EXPECTED_RESULT_FOR_JSON_EP, response.getBody().asString(),true);
    }

}
