package Listeners;

import Handlers.DriverManager;
import Utils.BrowserStackUtils;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MobileTestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        DriverManager _drivermanager = DriverManager.getInstance();
        AppiumDriver driver = _drivermanager.getDriver();

        BrowserStackUtils.setSessionStatus(driver,"passed","test passed!");

    }

    @Override
    public void onTestFailure(ITestResult result) {

        DriverManager _drivermanager = DriverManager.getInstance();
        AppiumDriver driver = _drivermanager.getDriver();

        BrowserStackUtils.setSessionStatus(driver,"failed",result.getThrowable().toString());

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
