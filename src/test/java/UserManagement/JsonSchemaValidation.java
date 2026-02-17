package UserManagement;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static utiles.JsonReader.getTestData;

public class JsonSchemaValidation {

    @Test
    public void validateJsonSchema() throws IOException, ParseException {
        // Code to validate JSON schema goes here
        // This is a placeholder for the actual implementation
        String headerKey = getTestData("headerKey"); // getting header key from JSON file
        String headerValue = getTestData("headerValue");
        File Schema = new File("resources/testData/ExpectedSchema.json");
        given()
                .headers(headerKey, headerValue)
            .when()
            .get("https://reqres.in/api/users?page=2")
            .then()
            .assertThat().statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchema(Schema));
        System.out.println("Validating JSON schema...");
    }
    // to test error scenarios , you can create another test method that intentionally fails the schema validation by providing an incorrect schema or by modifying the response in a way that it does not match the expected schema. This will help you ensure that your validation logic is working correctly and can handle
    // cases where the response does not conform to the expected structure.
}
