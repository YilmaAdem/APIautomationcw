package UserManagement;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import pojo.RetrieveResponseBody;

import java.io.IOException;

import static io.restassured.RestAssured.given;
public class ResponseWithPojoData {

    @Test
    public void testResponseWithObjectData() throws IOException {

                Response rep = given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:4000/resume");
                rep.then().statusCode(200);

                // Use org.json.JSONArray to parse the response
                JSONArray jo = new JSONArray(rep.getBody().asString());
                for (int i = 0; i < jo.length(); i++) {
                    JSONObject obj = jo.getJSONObject(i);
                    int id = obj.getInt("id");
                    String name = obj.getString("name");
                    String email = obj.getString("email");
                    boolean isInstructor = obj.getBoolean("isInstructor");
                    JSONObject socialLinks = obj.getJSONObject("socialLinks");
                    String skills = obj.getJSONArray("skills").toString();
                    String courses = obj.getJSONArray("courses").toString();

                    System.out.println("ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Email: " + email);
                    System.out.println("Is Instructor: " + isInstructor);
                    System.out.println("Skills: " + skills);
                    System.out.println("Courses: " + courses);
                    System.out.println("Social Links: " + socialLinks);
                }
                /* Result Set :    ID: 101
Name: Sidharth
Email: sidharth@example.com
Is Instructor: true
Skills: ["Automation Testing","SDET","Java","Playwright","AI for QA"]
Courses: [{"duration":"10 hours","topics":["HTTP Methods","Postman","Playwright API","Assertions"],"price":{"amount":49.99,"discount":20,"currency":"USD"},"rating":4.9,"studentsEnrolled":5200,"title":"API Automation Testing with Playwright","courseId":"API-2025"},{"duration":"8 hours","topics":["Variables","Functions","Async Await","Promises"],"price":{"amount":39.99,"discount":15,"currency":"USD"},"rating":4.8,"studentsEnrolled":4800,"title":"JavaScript for Testers","courseId":"JS-101"}]
Social Links: {"youtube":"https://youtube.com/@automatewithsidharth","website":"https://sidharthshukla.com","linkedin":"https://linkedin.com/in/sidharthshukla"}

===============================================
Default Suite
Total tests run: 1, Passes: 1, Failures: 0, Skips: 0
=============================================== */
            }


        @Test
        public void testJsoObjectResponsePojoBodyData(){
            RetrieveResponseBody resume = given()
                    .baseUri("http://localhost:4000")
                    .when()
                        .get("/resume")
                    .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .jsonPath()
                        .getObject("[0]", RetrieveResponseBody.class);
            System.out.println("ID: " + resume.getId());
            System.out.println("Name: " + resume.getName());
            System.out.println("Email: " + resume.getEmail());
            System.out.println("Is Instructor: " + resume.isInstructor());
            System.out.println("Skills: " + resume.getSkills());
            System.out.println("courses" + resume.getCourses());
            System.out.println("courses Name" + resume.getCourses().get(0).getTitle());
            System.out.println("courses Id  " + resume.getCourses().get(0).getCourseId());
            System.out.println("Social links " + resume.getSocialLinks());

    /*  Result set :  ID: 101
Name: Sidharth
Email: sidharth@example.com
Is Instructor: true
Skills: [Automation Testing, SDET, Java, Playwright, AI for QA]
courses[pojo.Course@7ef8eda7, pojo.Course@115ca7de]
courses NameAPI Automation Testing with Playwright
courses Id  API-2025
Social links pojo.SocialLinks@29fe4840

===============================================
Default Suite
Total tests run: 1, Passes: 1, Failures: 0, Skips: 0
===============================================


Process finished with exit code 0 */

        }


    }


