package UserManagement;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static io.restassured.RestAssured.given;


public class CalcTaxWithJsonFile {

    static FileInputStream fileInputStreamMethod(String postTaxrequestfile) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(new File(System.getProperty("user.dir") +
                    "/resources/testData/" + postTaxrequestfile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileInputStream;
    }

    @Test
    public void calculateTax() throws IOException {

        // This method will read a JSON file containing tax information and perform tax calculations.
        // The implementation will depend on the structure of the JSON file and the tax calculation logic.
        Response tax = given()
                .contentType(ContentType.JSON)
                .header("Authorization"," Bearer a189769b8a0b2d95edfade269b6968a7")
                .body(IOUtils.toString(fileInputStreamMethod("postTaxrequestfile.json")))
                .when()
                .post("https://api.taxjar.com/v2/taxes")
                .then()
                .extract().response();
        assert tax.statusCode() == 200 : "Expected status code 200 but got " + tax.statusCode();
        System.out.println("Tax calculation successful. status :" + tax.getStatusCode());
        System.out.println(tax.prettyPrint());
    }
}
