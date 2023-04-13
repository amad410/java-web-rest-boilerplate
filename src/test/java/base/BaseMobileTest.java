package base;

import Handlers.DriverManager;
import Pages.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import java.lang.reflect.Method;

public class BaseMobileTest {

    protected static ThreadLocal<AppiumDriver> _driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal <String> platform = new ThreadLocal<String>();

    private DriverManager _driverManager;
    BasePage _basePage;

    public BaseMobileTest() {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }
    @Parameters({"environment"})
    @BeforeSuite
    public void SuiteSetup(String environment) throws Exception {
        System.out.println("Setup Test Suite");
    }

    @Parameters({"platformName","deviceIndex"})
    @BeforeTest
    public void TestSetup(String platformName, String deviceIndex) throws Exception {
        setPlatform(platformName);

        try{
            _driverManager =  new DriverManager();
            _driverManager.initializeDriver(platformName, deviceIndex);
            setDriver(_driverManager.getDriver());
        }
        catch (Exception e) {
            throw new Exception("driver initialization failure. ABORT!!!\n" + e.toString());
        }
    }

    @BeforeMethod
    public void CreateTest(Method m) throws Exception {
        System.out.println("Creating Current Test");
        System.out.println("Starting Test: " + m.getName());
        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }

    @AfterTest
    public void TearDown(){
        if(_driverManager.getDriver() != null) {
            _driverManager.getDriver().quit();
            _driverManager.setDriver(null);
        }
    }
    @AfterClass
    public void Cleanup() throws Exception {
        System.out.println("Test Class Cleanup");

    }
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
    }
    public void setPlatform(String platform2) {
        platform.set(platform2);
    }

    public void setDriver(AppiumDriver driver) {
        _driver.set(driver);
    }
    public AppiumDriver getDriver() {
        return _driver.get();
    }
    public void closeApp() {
        ((InteractsWithApps) getDriver()).closeApp();
    }

    public void launchApp() {
        ((InteractsWithApps) getDriver()).launchApp();
    }

}
