package utiles;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {

    //changing to singleton pattern to ensure only one instance of SoftAssert is used throughout the tests
   /* private SoftAssert softAssert;*/

    private static SoftAssert softAssertInstance;
    // private constructor to prevent instantiation
    private SoftAssertionUtil(){}

    // method to get the single instance of SoftAssert
    // restricting the instantiation of the class to one object and
    // providing a global point of access to it
    public static SoftAssert getInstance() {
        if (softAssertInstance == null) {
            softAssertInstance = new SoftAssert();
        }
        return softAssertInstance; // return the single instance of SoftAssert
    }

   /* public SoftAssertionUtil() {
        softAssert = new SoftAssert();
    }*/



// this void class will handle soft assertions with custom messages
    public static void assertTrue(boolean condition, String message) {

        try {
            getInstance().assertTrue(condition, message); // replacing assert with getInstance() to ensure we are using the singleton instance of SoftAssert
        } catch (AssertionError e) {
            System.err.println("Assertion failed: " + message);
            throw e;
        }

    }
    // this void class will handle assertion equals with custom messages.

    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            getInstance().assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            System.err.println("Assertion failed: " + message);
            throw e;
        }
    }

    public static void assertAll() {
        getInstance().assertAll(); // calling assertAll on the singleton instance of SoftAssert to execute all collected assertions
    }
}
