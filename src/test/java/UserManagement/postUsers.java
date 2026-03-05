package UserManagement;

import core.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import pojo.PatchResponseBody;
import pojo.postRequestBody;
import utiles.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;

public class postUsers {

    //since we are using the body again and again for different methods, we can create a method to read the body from JSON file
    // and return it as string and use that method in all the test cases
    //instead of writing the code again and again to read the body from JSON file and convert it to string
   static FileInputStream  fileInputStreamMethod(String postrequestfile) {
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
    public void validatePostWithStringBody() throws IOException, ParseException {
        // converting JSON file data to string and passing it in body
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json").
                        body("{\"name\":\"Mic\",\"job\":\"GrandPapa\"}")
                        .when().
                        post("https://reqres.in/api/users");

        assertEquals(resp.getStatusCode(), StatusCode.Created.getCode());
        System.out.println("Response Body is: " + resp.body().asString());//printing response body in console
    }

    @Test
    public void validatePutWithStringBody() throws IOException, ParseException {
        // converting JSON file data to string and passing it in body
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json").
                        body("{\"name\":\"Nahum\",\"job\":\"Student\"}")
                        .when().
                        put("https://reqres.in/api/users/2");

        assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.getCode());
        //System.out.println("Response Body is: \n"  + resp.body().asString());//printing response body in console
        System.out.println((resp.body().prettyPrint()));
    }

    @Test
    public void validatePatchWithStringBody() throws IOException, ParseException {
        // converting JSON file data to string and passing it in body
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json")
                         .body("{\"job\":\"Professor\"}")
                        .when().
                        patch("https://reqres.in/api/users/2");

        assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.getCode());
        //System.out.println("Response Body is: \n"  + resp.body().asString());//printing response body in console
        System.out.println((resp.body().prettyPrint()));
    }

    @Test
    // loading json file and passing it in body
    public void validatePostWithJsonfile() throws IOException, ParseException {
        // converting JSON file data to string and passing it in body
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json").
                        body(IOUtils.toString(
                                new FileInputStream(new File(System.getProperty("user.dir") +
                                        "/resources/testData/postrequestfile.json"))))
                        .when().
                        post("https://reqres.in/api/users");
        assertEquals(resp.getStatusCode(), StatusCode.Created.getCode());
        //System.out.println("Response Body is: \n"  + resp.body().asString());//printing response body in console
        System.out.println((resp.body().prettyPrint()));


    }
    @Test
    //using private static method und class method to load json file and passing it in body
    public void validatePostWithJsonfilePrivate() throws IOException, ParseException {
        // converting JSON file data to string and passing it in body
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json").
                        body(IOUtils.toString(fileInputStreamMethod( "postrequestfile.json")))
                        .when().
                        post("https://reqres.in/api/users");
        assertEquals(resp.getStatusCode(), StatusCode.Created.getCode());
        //System.out.println("Response Body is: \n"  + resp.body().asString());//printing response body in console
        System.out.println((resp.body().prettyPrint()));


    }
    @Test(groups = {"RegressionSuite"})
    public void validatePostWithPojo() throws IOException, ParseException {
       // ExtentReport.extentlog = ExtentReport.extentreport.startTest("verify status code for POST method",
          //      "Validate 201 status code for POST method using property file");
        // converting JSON file data to string and passing it in body
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file

        postRequestBody data = new postRequestBody(); //creating an object of postRequestBody class to set the values for the fields in the class
        //converted the Java object to JSON format by using the setter methods of the postRequestBody class
        // and passing the values to the fields
        data.setFirst_name("Nahum");
        data.setLast_name("Yilma");
        data.setJob("Student");

        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json")
                        .body(data)
                        .when().
                             post("https://reqres.in/api/users");
        assertEquals(resp.getStatusCode(), StatusCode.Created.getCode());
        System.out.println("Response Body is: " + resp.body().prettyPrint());//printing response body in console
    }

    @Test
    public void validatePostWithPojoList() throws IOException, ParseException {
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file

       //
        List<String> listLanguage = new ArrayList<>();
        listLanguage.add("Java");
        listLanguage.add("Python");

        postRequestBody data = new postRequestBody();

        //converted the Java object to JSON format by using the setter methods of the postRequestBody class
        // and passing the values to the fields
        data.setFirst_name("Nahum");
        data.setLast_name("Yilma");
        data.setJob("Student");
        data.setLanguages(listLanguage);

        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json")
                        .body(data)
                        .when().
                        post("https://reqres.in/api/users");
        assertEquals(resp.getStatusCode(), StatusCode.Created.getCode());
        System.out.println("Response Body is: " + resp.body().prettyPrint());//printing response body in console
    }
    @Test
    public void validatePutWithResponsePojo() throws IOException, ParseException {
        // Deserialization: converting JSON response to Java object by using the postRequestBody class
        String headerKey = JsonReader.getTestData("headerKey"); // getting header key from JSON file
        String headerValue = JsonReader.getTestData("headerValue");// getting header value from JSON file
        //PUT method is used to update the existing data, so we are updating the job field of the user with id 2
        //String job = "Student";
        postRequestBody putRequest = new postRequestBody();
        putRequest.setFirst_name("Yilma");
        putRequest.setJob("Professor");

        Response resp =
                given().
                        headers(headerKey, headerValue). //example query param
                        header("Content-Type", "application/json")
                        .body(putRequest)
                        .when().
                         put("https://reqres.in/api/users/2");
        //Deserialization: converting JSON response to Java object by using the postRequestBody class
        assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.getCode());
        System.out.println("Put pojo is executed successfully");//printing response body in console
        System.out.println(resp.body().prettyPrint());//printing response body in console
    }

    @Test
    public void validatePatchWithResponsePojo() throws IOException, ParseException {

        String headerKey = JsonReader.getTestData("headerKey");
        String headerValue = JsonReader.getTestData("headerValue");
        //serialization: converting Java object to JSON format by using the setter methods of the postRequestBody class
        String job = "Professor";
        String first_name = "Nahum";
        String last_name = "Yilma";
        PatchResponseBody patchRequest = new PatchResponseBody();//
        patchRequest.setJob(job);
        patchRequest.setFirst_name(first_name);
        patchRequest.setLast_name(last_name);
        Response resp =
                given()
                        .header(headerKey, headerValue)
                        .contentType(ContentType.JSON)
                        .body(patchRequest)
                        .when()
                        .patch("https://reqres.in/api/users/2");
    //Deserialization: converting JSON response to Java object by using the PatchResponseBody class
        PatchResponseBody responseBody = resp.as(PatchResponseBody.class);
        System.out.println(responseBody.getJob());
        assertEquals(200, resp.getStatusCode());
        assertEquals(job, responseBody.getJob());
        System.out.println("Patch pojo is executed successfully");
        System.out.println(resp.getBody().prettyPrint());
    }



}
