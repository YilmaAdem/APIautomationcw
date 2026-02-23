package UserManagement;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;

// builder pattern is a design pattern that allows for the step-by-step construction of complex objects. It provides a clear and fluent interface for creating objects with multiple optional parameters, making the code more readable and maintainable. In the context of RestAssured, the builder
// pattern can be used to create request specifications in a more organized and reusable way.
public class BuilderPatternImplementation {

    private RequestSpecification requestSpec; //provided by rest assured to define the structure of the request, including base URI, headers, query parameters, and content type. It allows us to create a reusable specification
    // for our API requests, making our test code cleaner and more maintainable.

    private ResponseSpecification responseSpec; // provided by rest assured to define the expected structure of the response, including status code, headers, and body content. It allows us to create a reusable specification for validating API responses, making our test code cleaner and more maintainable.
    // normal approach without using builder pattern. Compare it to below the builder pattern approach to see the difference in readability and maintainability of the code.
    @Test
    public void testRestAssuredNormalApproach() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .contentType(ContentType.JSON)
                .queryParam("userId", "1")
                .header("Authorization", "Bearer my-token")
                .when()
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(200);
    }
    // builder pattern approach

    @Test
    public void testRestAssuredBuilderPattern() {
        // using the builder pattern to create a request specification
        requestSpec = getRequestSpecBuilder("1", "application/json", "Authorization", "Bearer my-token");

        given()
                .spec(requestSpec)
                .when()
                .get("/posts")
                .then()
                .spec(getResponseSpecBuilder(200, "application/json"));// using the builder pattern to create a response specification
        System.out.println("Response specification created using builder pattern");

    }


    //creating private method to build the request specification using the builder pattern,
    // this method takes parameters for query parameter, content type, header key and header value
    // to create a reusable request specification that can be used across multiple tests.
    private RequestSpecification getRequestSpecBuilder(String queryParam, String ContentType, String headerKey, String headerValue) {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(ContentType)
                .addQueryParam("userId", queryParam)
                .addHeader(headerKey, headerValue)
                .build();
        return requestSpec;
    }

    // response specification can also be created using the builder pattern, similar to request specification, to define the expected response structure, status code, and other response-related parameters. This can help in validating
    //  the response in a more organized and reusable way across multiple tests.
    private ResponseSpecification getResponseSpecBuilder(int statusCode, String ContentType) {
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType)
                .build();
        return responseSpec;
    }
}