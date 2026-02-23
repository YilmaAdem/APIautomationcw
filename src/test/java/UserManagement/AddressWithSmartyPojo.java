package UserManagement;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

    public class AddressWithSmartyPojo {

        @Test
        public void testAddressValidation()
         {
            // Build address JSON object
            JSONObject address = new JSONObject();
            address.put("street", "1600 Amphitheatre Pkwy");
            address.put("city", "Mountain View");
            address.put("state", "CA");
            address.put("zipcode", "94043");
            address.put("input_id", "Shipping");

            // Add to JSON array
            JSONArray addressArray = new JSONArray();
            addressArray.add(address);

            // Send POST request
            Response response = given()
                    .queryParam("auth-id", "a3a1a936-d288-7582-1e96-d41b77e689b1")
                    .queryParam("auth-token", "I573oQqlhVYSNmtIeM95")
                    .contentType(ContentType.JSON)
                    .body(addressArray.toString())
                    .when()
                    .post("https://us-street.api.smarty.com/street-address")
                    .then()
                    .extract().response();

            System.out.println(response.prettyPrint());
        }
    }