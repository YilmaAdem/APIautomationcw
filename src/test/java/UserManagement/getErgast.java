package UserManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class getErgast extends core.BaseTest {
    @Test
    // using query parameter to get first list data and validate data list in response
    public void testGetUserWithQueryParam() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response =
                given().
                        queryParam("page", 2).
                        headers("x-api-key", "reqres_c7637373bd1742b8be2ea1ff513d545f"). //example query param
                        when().
                        get("/users").
                        then().statusCode(200).
                        extract().
                        response();
        // validate data that hasSize equal to 6
        response.then().body("data", hasSize(6));
        // validate first data in the list is correct
        response.then().body("data[0].id", equalTo(7));
        response.then().body("data[0].email", is("michael.lawson@reqres.in"));
        response.then().body("data[0].first_name", is("Michael"));
        response.then().body("data[0].last_name", is("Lawson"));
        response.then().body("data[0].avatar", is("https://reqres.in/img/faces/7-image.jpg"));

        //Validate second data in the list is correct
        response.then().body("data[1].id", equalTo(8));
        response.then().body("data[1].email", is("lindsay.ferguson@reqres.in"));
        response.then().body("data[1].first_name", is("Lindsay"));
        response.then().body("data[1].last_name", is("Ferguson"));
        response.then().body("data[1].avatar", is("https://reqres.in/img/faces/8-image.jpg"));

    }
    @Test
    // validate response with path parameter
    public void validatResponseBodyGetPathParam() {

        String raceSeasonValue = "2025";
        Response response =
                given().
                        //headers("x-api-key","ergast_c7637373bd1742b8be2ea1ff513d545f"). //example query param
                                pathParam("raceSeason", raceSeasonValue). //passing key value for path parameter
                        when().
                        get("https://api.jolpi.ca/ergast/f1/{raceSeason}/races.json").
                        then().statusCode(200).
                        extract().
                        response();
        response.prettyPrint();
        // validate that season in response is equal to raceSeasonValue
        int actualStatusCode = response.statusCode();
        assertThat(actualStatusCode, equalTo(200));
        System.out.println("Output" + response.body().prettyPrint());


    }

}
