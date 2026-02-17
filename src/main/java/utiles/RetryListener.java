package utiles;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        //This method is called by TestNG for each test method.
        // It checks if the test method has a retry analyzer assigned.

        if (annotation.getRetryAnalyzerClass() == null) {
            annotation.setRetryAnalyzer(FailRetry.class);
        }
    }
}
