package core;

import com.relevantcodes.extentreports.LogStatus;
import helper.Helper;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utiles.ExtentReport;

import java.io.IOException;

public class BaseTest {

    // Set base URI and port before the test
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

  /*  @Test
    public void testUploadDownload() { //
        File file = new File("resources/testupload.txt");
        Response response = given().
                multiPart("file", file).
                when().
                post("/upload").
                then().statusCode(200).
                extract().
                response();
        System.out.println("This is a test case for upload and download functionality.");
    }
    */
    //
    @BeforeSuite(alwaysRun = true)
    public void config() throws IOException {
        Helper baseTestHelper = new Helper();
        //Create the path in which we will create folder to keep html reports
        String subfolderpath=System.getProperty("user.dir")+"/reports/"+ baseTestHelper.Timestamp();
        //create sub folder
        baseTestHelper.CreateFolder(subfolderpath);

        ExtentReport.initialize(subfolderpath+"/"+"APIAutomation-execution-report.html");
    }

    @BeforeMethod(alwaysRun = true)
    public static void LogBeforeMethod() {
        //final Logging log = Logging.getInstance();
        System.out.println("Test Case Execution Started >>>>>>>>>>>>>>>>>>>>>>>");

        //log.info("Test case", "*********************************************************************");
    }

    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) {// ITestResult is from testng. This method is executed after each test method and logs
        // the result of the test case in the Extent Report.
        ExtentReport.extentlog = ExtentReport.extentreport.startTest(result.getName());
        if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentReport.extentlog.log(LogStatus.PASS, "Test Case : "+ result.getName()+" is passed ");

        } else if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReport.extentlog.log(LogStatus.FAIL, "Test case : "+ result.getName()+" is failed ");
            ExtentReport.extentlog.log(LogStatus.FAIL, "Test case is failed due to:  " + result.getThrowable());

        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentReport.extentlog.log(LogStatus.SKIP, "Test case is Skiped " + result.getName());
        }
        ExtentReport.extentreport.endTest(ExtentReport.extentlog);
    }

    @AfterSuite(alwaysRun = true)
    public void endReport() {
        //ExtentReport.extentreport.flush();
        ExtentReport.extentreport.close();
        System.out.println("Test Case Execution Ended >>>>>>>>>>>>>>>>>>>>>>>");
    }
}


