package UserManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utiles.ExtentReport;
import utiles.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;

public class UserWorkFlow {
    private static String userId; //variable to store user ID for chaining

    @BeforeClass
    public void setup() {
        ExtentReport.initialize(System.getProperty("user.dir") + "/resources/extent-config.xml");
    }

    static FileInputStream fileInputStreamMethod(String postrequestfile) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(new File(System.getProperty("user.dir") +
                    "/resources/testData/" + postrequestfile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileInputStream;
    }

    @Test
    public void createUser() throws IOException, ParseException {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("verifyStatusCodeCreate",
                "Validate 201 status code for Create method");
        // Code to create a user
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json").
                             body("{\"name\":\"John Doe\",\"job\":\"QA Engineer\"}")
                        .when().
                            post("https://reqres.in/api/users")
                        .then()
                            .extract().response();

        assertEquals(resp.getStatusCode(), StatusCode.Created.getCode());
        String userId = resp.jsonPath().getString("id");
        System.out.println("Response Body is: " + resp.body().prettyPrint());//printing response body in console
        System.out.println("User created successfully with status code: " + resp.getStatusCode());
        System.out.println("Created User ID: " + userId);
    }

    @Test
    public void readUser() throws IOException, ParseException {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("verifyStatusCodeRead",
                "Validate 200 status code for  read method");
        // Code to read a user

        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json")
                        .when().
                            get("https://reqres.in/api/users/" + userId)
                        .then()
                           // .statusCode(200)
                            .extract().response();

        userId = resp.jsonPath().getString("id");
        System.out.println("readUser() is successfully executed");
    }
    @Test
    public void updateUser() throws IOException, ParseException{
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("verifyStatusCodeUpdate",
                "Validate 200 status code for update method");
        // Code to update a user
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        Response resp =
                (Response) given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json")
                        .body("{\"name\":\"John Updated\",\"job\":\"Senior QA\"}")
                        .when().
                             put("https://reqres.in/api/users/2" )
                        .then()
                            .extract().response();

        //userId = resp.jsonPath().getString("id");
        assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.getCode());
        System.out.println("Status code is: " + resp.getStatusCode());
        System.out.println("Response Body is: " + resp.body().prettyPrint());
        System.out.println("update is successfully run" );

    }
    @Test
    public void deleteUser() throws IOException, ParseException{
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("verifyStatusCodeDelete",
                "Validate 204 status code for  DELETE method");
        // Code to delete a user
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        Response resp =
                (Response) given().
                        headers(headerKey, headerValue). //example query param
                                header("Content-Type", "application/json")

                        .when()
                            .delete("https://reqres.in/api/users/" + userId)
                         .then()
                            .extract().response();


        //userId = resp.jsonPath().getString("id");
        assertEquals(resp.getStatusCode(), StatusCode.NO_CONTENT.getCode());
        System.out.println("Status code is: " + resp.getStatusCode());
        System.out.println("delete User method is successfully executed");


    }
}
