package UserManagement;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadDownload {

    @Test
    public void testUploadDownload() {
        // This is a placeholder for the upload and download test case.
        // You can implement the logic for uploading and downloading files here.

        File file = new File("resources/testupload.txt"); // Create a file object for the file you want to upload or download
        if(!file.exists()) {
            throw new RuntimeException("File does not exist: " + file.getAbsolutePath());
        }
        Response response = (Response) given().
                multiPart("file", file). // Use the multiPart method to specify the file to be uploaded
                when().
                post("/upload");// Specify the endpoint for uploading the file

        System.out.println("This is a test case for upload and download functionality.");
    }
}
