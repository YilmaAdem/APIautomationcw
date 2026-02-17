package UserManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utiles.ExtentReport;
import utiles.JsonReader;
import utiles.PropertyReader;
import utiles.SoftAssertionUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static utiles.JsonReader.getJsonArray;


public class getUsers extends BaseTest {
    //SoftAssertionUtil softAssertion = new SoftAssertionUtil();

    @DataProvider(name = "userData") // TestNG Data Provider example
    public Object[][] userData() {
        return new Object[][] {
            {"1", "Yilma"},
            {"2", "Betty"},
            {"3", "Nahum"}
        };
    }

    @Test(dataProvider = "userData") // Using TestNG  Data Provider to supply test data
    @Parameters({"id","name"})
    public void testGetUserById(String id, String name) {
        given()
                .queryParam("id", id)
                .queryParam("name", name)
                .headers("x-api-key", "reqres_c7637373bd1742b8be2ea1ff513d545f")
        .when()
                .get("https://reqres.in/api/users")
        .then()
                .statusCode(200);
        System.out.println("Testing user with ID: " + id + " and Name: " + name);
        // Here you can add your test logic to get user by ID and validate the name
    }


    @Test
    public void getUsers() {
// using amcrest matchers to validate status code
        given().
                when().get("https://conduit-api.bondaracademy.com/api/tags").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void validateGetResponseBody() {
// using Amcrest matchers to validate response body
        given().
                when().
                get("https://jsonplaceholder.typicode.com/posts/1").
                then().
                assertThat().
                statusCode(200).
                body("userId", equalTo(1)).
                body("id", equalTo(1)).
                body("title", equalTo(
                        "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
                ));
    }

    @Test

    public void validateResponseHasItem() {   // Using Amcrest matchers HasItem
// HasItems example. Two titles to be verified in the response
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response =
                given().
                        when().
                        get("/posts").
                        then().
                        extract().
                        response();

        assertThat(
                response.jsonPath().getList("title"),
                hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "qui est esse")
        );
    }

    @Test
    public void validateResponseHasSize() {
// hasSize example . Total number of comments = 500
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response =
                given().
                        when().
                        get("/comments").
                        then().
                        extract().
                        response();

        assertThat(
                response.jsonPath().getList("").size(),
                equalTo(500)
        );
    }


    @Test
    public void validateallTags() {
        List<String> tags =
                given().
                        when().
                        get("https://conduit-api.bondaracademy.com/api/tags").
                        then().
                        assertThat().
                        statusCode(200).
                        extract().
                        path("tags");
        System.out.println(tags);
        assert tags.contains("Test");
        assert tags.size() > 0;

    }

    // using Amcrest matchers  contains to validate that list contains specific tag
    @Test

    public void validateListContainsInOrder() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Response response =
                given().
                        when().
                        get("/comments?postId=1").
                        then().
                        extract().
                        response();
        // use amcrest matchers to validate that list contains specific emails in order
        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz",
                "Lew@alysha.tv", "Hayden@althea.biz");
        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));
    }


    @Test(groups = {"RegressionSuite"})
    //using Map to create headers for the request
    public void testTwoHeadersWithMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-api-key", "reqres_c7637373bd1742b8be2ea1ff513d545f");
        headers.put("Content-Type", "application/json");
        Response response =
                given().
                        headers(headers).
                        when().
                        get("https://reqres.in/api/users/2").
                        then().
                        statusCode(200).
                        // extract().response();
                                extract().response();
        System.out.println("successful with two headers using Map and HashMap");

    }
/*
 Different ways to create POST request body:
    1. Using HashMap
    2. Using POJO class
    3. POST request body  using org.json library to create JSON object and then convert it to string
    4. Using JSON file to read the request body and then convert it to string

 */
    @Test
    public void createUserPostWithHashMap() {
        //Create HashMap for request body

        HashMap data = new HashMap();
        data.put("first_name", "Janet");
        data.put("last_name", "Weaver");

        given()
                .header("x-api-key", "reqres_c7637373bd1742b8be2ea1ff513d545f")
                .header("Content-Type", "application/json")
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("first_name", equalTo(data.get("first_name")))
                .log().all();
    }
    @Test
    public void createUserPostUsingJsonObject() {
        //Create JSON object for request body using org.json library
        JSONObject data = new JSONObject();
            data.put("first_name", "Yilma");
            data.put("last_name", "Adem");

        given()
                .header("x-api-key", "reqres_c7637373bd1742b8be2ea1ff513d545f")
                .header("Content-Type", "application/json")
                .body(data.toString())
                .when()
                    .post("https://reqres.in/api/users")
                .then()
                    .statusCode(201)
                    .body("first_name", equalTo(("Yilma")))
                    .body("last_name", equalTo(("Adem"))).log().all();
        System.out.println("successful with POST request body using JSON object and org.json library");
    }

    @Test(groups = {"RegressionSuite"})
    public void testFetchHeaders() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("verify if server header is cloudflare",
                "Validate server header value using Rest Assured Headers class");
        Response response =
                given().
                        when().
                        get("https://reqres.in/api/users/2").
                        then().
                        extract().response();
        //fetch headers using header class from rest assured
        Headers allHeaders = response.getHeaders();
        for (Header h : allHeaders) {
            // System.out.println(h.getName() + ": " + h.getValue());

            if (h.getName().equalsIgnoreCase("server")) {
                assertThat(h.getValue(), equalTo("cloudflare"));
                System.out.println("Rest assured Headers class and value  " + h.getName() + ": " + h.getValue());
            }

        }

    }



    @Test(groups = {"RegressionSuite"})
    //validate delete method
    public void verifyDeleteMethod() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("verifyStatusCodeDelete",
                "Validate 204 status code for  DELETE method");
        Response resp =
                given()
                        .header("x-api-key", "reqres_c7637373bd1742b8be2ea1ff513d545f")
                        .delete("https://reqres.in/api/users/2");
        assertThat(resp.getStatusCode(), equalTo(StatusCode.NO_CONTENT.getCode())); // status code is from enum class
        System.out.println("verifyDeleteMethod executed successfully");

    }

    @Test(groups = {"RegressionSuite"})
    //validate delete method using test data from JSON file
    public void verifyDeleteMethodWithTestDataFromJson() throws IOException, ParseException {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("verifyStatusCodeDelete",
                "Validate 204 status code for  DELETE method");
        String headerKey = JsonReader.getTestData("headerKey");
        String headerValue = JsonReader.getTestData("headerValue");
        System.out.println(headerKey + " " + headerValue);
        Response resp =
                given()
                        .header(headerKey, headerValue)
                        //     .header("x-api-key", "reqres_c7637373bd1742b8be2ea1ff513d545f")
                        .delete("https://reqres.in/api/users/2");
        assertThat(resp.getStatusCode(), equalTo(StatusCode.NO_CONTENT.getCode())); // status code is from enum class
        System.out.println("verifyDeleteMethodWithTestDataFromJson executed successfully");

    }
    // using Extent Report to log test steps
    @Test(groups = {"RegressionSuite"})
    public void verifyStatusCodeDelete(){
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("verifyStatusCodeDelete",
                "Validate 204 status code for  DELETE method");
        Response resp =
                given()
                        .header("x-api-key", "reqres_c7637373bd1742b8be2ea1ff513d545f")
                        .delete("https://reqres.in/api/users/2");
        assertThat(resp.getStatusCode(), equalTo(StatusCode.NO_CONTENT.getCode())); // status code is from enum class
        System.out.println("verifyStatusCodeDelete executed successfully");

    }

    @Test(groups = {"RegressionSuite"})
    public void validateGetMethodWithPropertyFile() throws IOException, ParseException {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("verify status code for GET method",
                "Validate 200 status code for GET method using property file");
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        String serverAddress = PropertyReader.propertyReader("resources//config.properties", "server");
        //getting server address from property file
        System.out.println("Server address from property file: " + serverAddress);
        Response resp =
                given().
                        header(headerKey, headerValue). // getting header key and value from JSON file
                        queryParam("page", 2).
                        when()
                        .get(serverAddress); // getting URL from property file
        int actualStatusCode = resp.statusCode();
        // assertThat(actualStatusCode, equalTo(200));
        assertThat(resp.getStatusCode(), equalTo(StatusCode.SUCCESS.getCode())); // status code is from enum class
        System.out.println("validateGetMethodWithPropertyFile executed successfully");

    }

    @Test
    //combining property file and test data from JSON file
    public void validateFromPropertyFile_TestData() throws IOException, ParseException {
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        String serverAddress = PropertyReader.propertyReader("resources//config.properties", "serverAddress");
        String endpoint = JsonReader.getTestData("endpoint");
        String fullURL = serverAddress + endpoint;
        System.out.println("Full URL: " + fullURL);
        Response resp =
                given().
                        header(headerKey, headerValue). // getting header key and value from JSON file
                        queryParam("page", 2).
                        when()
                        .get(fullURL); // getting full URL from property file and JSON file
        int actualStatusCode = resp.statusCode();//rest assured status code method
        assertThat(actualStatusCode, equalTo(200));
        System.out.println("validateFromPropertyFile_TestData executed successfully " + fullURL);


    }

    @Test
    public void softAssertion() { // continues execution even when assertion fails but if add assertAll()
        // at the end then it will fail the test
        //when you have multiple assertions, use soft assertion
        SoftAssert softAssertion = new SoftAssert();
        System.out.println("softAssert");
        softAssertion.assertTrue(false);
        softAssertion.assertTrue(true);
        // softAssertion.assertAll(); // tells TestNG to evaluate all soft assertions

    }

    @Test
    public void hardAssertion() { // gives AssertionError immediately when assertion fails
        //when we have single assertion , use hard assertion
        System.out.println("hardAssert");
        Assert.assertTrue(true);
        System.out.println("hardAssert");
    }

    @Test
    public void softAssertionWithWrapperUtil() { // let use the SoftAssertionUtil class to handle soft assertions
        // at the end then it will fail the test
        //when you have multiple assertions, use soft assertion
        //SoftAssertionUtil softAssertion = new SoftAssertionUtil(); // using the SoftAssertionUtil class
        System.out.println("Soft Assertion With Wrapper from Util");
        SoftAssertionUtil.assertTrue(false, "First soft assertion failed");// custom message
        SoftAssertionUtil.assertTrue(true, "Second soft assertion passed");
        // softAssertion.assertAll(); // tells TestNG to evaluate all soft assertions

    }

    @Test
    public void validateWithSoftAssertUtil() throws IOException, ParseException {// assertion using SoftAssertionUtil class
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        RestAssured.baseURI = "https://reqres.in/api";
        Response resp =
                given().
                        queryParam("page", 2).
                        headers(headerKey,headerValue ). //example query param
                        when().
                        get("/users").
                        then().statusCode(200).
                        extract().
                        response();
        // validate data that hasSize equal to 6
        resp.then().body("data", hasSize(6));
        int actualStatusCode = resp.statusCode();
        // validate with SoftAssertionUtil class
      SoftAssertionUtil.assertEquals(resp.getStatusCode(),StatusCode.SUCCESS.getCode(), "Status code validation passed");
       SoftAssertionUtil.assertAll();
       System.out.println("validateWithSoftAssertUtil executed successfully");

    }

    @Test
    public void Test() throws IOException, ParseException{
        JSONArray jsonArray = getJsonArray("contact");// calling getJsonArray method to get the JSON array based on the key "contact"
        String lang = JsonReader.getJsonArrayData("language", 1);// calling getJsonArrayData method to get the value at index 1
        // in the JSON array based on the key "language"
        String lang2 = JsonReader.getJsonArrayData("technology", 0);
        String contact = JsonReader.getJsonArrayData("contact", 0);
        //Integer num = JsonReader.<Integer>getJsonArrayData("numbers", 1);

        System.out.println("Languages & technology => "  + lang + " : " +     lang2);
        System.out.println("contacts "  + jsonArray);
        System.out.println("Out put "  + contact);

    }

}

