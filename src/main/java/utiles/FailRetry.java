package utiles;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

// This class implements the IRetryAnalyzer interface to provide a mechanism for retrying failed test cases.
public class FailRetry implements IRetryAnalyzer { // implementing the IRetryAnalyzer interface to create a custom
    // retry mechanism for failed test cases

    private int retryCount = 0; //

    private static final int maxRetryCount = 2; // Set the maximum number of retries

    @Override
    public boolean retry(ITestResult result) {// This method is called by TestNG when a test case fails.
        // It checks if the retry count is less than the maximum retry count.
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true; // Retry the test
        }
        return false; // Do not retry the test
    }


}
