package UserManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;

public class getPostmanEcho {

        @Test
        public void validateUserWithJsonBody() {
            Response response =
                    given().
                            contentType("application/json").
                            header("x-api-key", "reqres_c7637373bd1742b8be2ea1ff513d545f").
                            body("{ \"name\": \"Betty\", \"job\": \"Accounting Manager\" }").
                            when().
                            post("https://reqres.in/api/users").
                            then().
                            statusCode(201).
                            extract().response();
            response.prettyPrint();
            // Validate response body
            //assertThat(response.jsonPath().getString("name"), equalTo("morpheus"));
            assertThat(response.jsonPath().getString("name"), equalTo("Betty"));
            assertThat(response.jsonPath().getString("job"), equalTo("Accounting Manager"));
            System.out.println("Output" + response.body().prettyPrint());

        }
        @Test
        // to validate basic authentication
        public void validateResponseWithBasicAuth() {
            Response resp = given()
                    .auth()
                    //      .preemptive()
                    .basic("postman", "password") // correct username and password
                    .when()
                    .get("https://postman-echo.com/basic-auth");//rest assured basic auth endpoint
            //  .then()
            //.statusCode(200)
            //.extract()
            //.response();
            // using status code method to validate status code . Save it in variable and assert
            int actualStatusCode = resp.statusCode();
            assertEquals(actualStatusCode, 200);
            System.out.println(resp.body().asString());

        }

    @Test
    // to validate basic authentication
    public void validateResponseWithDigestAuth() {
        Response resp = given()
                .auth()
                //      .preemptive()
                .digest("postman", "password") // correct username and password
                .when()
                .get("https://postman-echo.com/digest-auth");//rest assured basic auth endpoint
        //  .then()
        //.statusCode(200)
        //.extract()
        //.response();
        // using status code method to validate status code . Save it in variable and assert
        int actualStatusCode = resp.statusCode();
        assertEquals(actualStatusCode, StatusCode.SUCCESS.getCode()); // using enum class for status code
        System.out.println(resp.body().asString());

    }

}

