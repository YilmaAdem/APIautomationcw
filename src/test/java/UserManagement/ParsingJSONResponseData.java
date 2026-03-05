package UserManagement;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ParsingJSONResponseData {

    @Test(description = "This is approach 1")
    public void testJsonResponseBodyData() {

        //Approach 1

        Response rep = (Response) given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/book");
        rep.then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .body("[0].title", equalTo("Clean Code"))
                .body("[0].author", equalTo("Robert C. Martin"));
        System.out.println(" successfully retrieved the book details from the API");
        // System.out.println(rep.getBody().prettyPrint());

    }

    @Test(description = "This is approach 2")
    void testJsoObjectResponseBodyData() {
        
        Response rep = (Response) given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/book");
        rep.then().statusCode(200);
        JSONArray jo = new JSONArray(rep.getBody().asString());
         for (int i=0; i<jo.length(); i++){
                String title = jo.getJSONObject(i).getString("title");
                String author = jo.getJSONObject(i).getString("author");
                System.out.println("Book Title: " + title);
                System.out.println("Book Author: " + author);
            /*
            Book Title: Clean Code
            Book Author: Robert C. Martin
            Book Title: Design Patterns
            Book Author: Erich Gamma
             */
         }
    }
}
