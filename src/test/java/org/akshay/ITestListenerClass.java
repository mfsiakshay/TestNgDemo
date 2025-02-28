package org.akshay;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestListenerClass extends BaseTest implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        captureScreenshot(result.getTestContext().getName()+"_"+result.getMethod().getMethodName());
    }
}
