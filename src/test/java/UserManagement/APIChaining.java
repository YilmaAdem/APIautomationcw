package UserManagement;

import core.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;

public class APIChaining extends BaseTest {

    private String generateAuthToken() { //method to generate auth token for user
        Response resp =
                given().
                        header("Content-Type", "application/json").
                        body("{\"userName\":\"yilmaApi\",\"password\":\"Test1234$\"}")
                        .when()
                        .post("https://bookstore.toolsqa.com/Account/v1/GenerateToken");

        assertEquals(resp.getStatusCode(), 200);
        //System.out.println("Response Body is: \n"  + resp.body().asString());//printing response body in console
        //System.out.println((resp.body().prettyPrint()));
        String responseToken = resp.path("token");
        System.out.println("Generated Token is: " + responseToken);
        return responseToken;
    }
    @Test
    public void verifyBookstoreAddBooks(){
        String token = generateAuthToken(); //calling method to generate auth token for user
        Response resp =
                given().
                        header("Content-Type", "application/json").
                        header("Authorization", "Bearer " + token).
                        body("{\"userId\":\"e9e693e4-3dc9-4bf2-b3e0-427fcd0b623a\",\"collectionOfIsbns\":[{\"isbn\":\"9781593275846\"}]}")
                        .when()
                        .post("https://bookstore.toolsqa.com/BookStore/v1/Books");
       //assertEquals(resp.getStatusCode(), StatusCode.Created.getCode());
       // Assert.assertEquals(resp.getStatusCode(), 201);
        System.out.println(resp.getBody().asString());
        System.out.println("Book added successfully to the bookstore");
        System.out.println((resp.body().prettyPrint()));

    }

}
