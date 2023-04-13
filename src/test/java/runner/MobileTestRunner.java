package runner;

import Utils.BrowserStackUtils;
import base.BaseMobileTest;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import io.cucumber.testng.*;
import org.testng.annotations.*;
import java.lang.reflect.Method;


@CucumberOptions(features={"src/test/resources/features"},
        glue = {"sampleSteps"},
        monochrome = true,
        plugin = {"pretty",
                "html:target/cucumber-html-report.html","json:target/cucumber.json"},
        tags = "@Mobile")

@Listeners({ExtentITestListenerClassAdapter.class})
public class MobileTestRunner extends AbstractTestNGCucumberTests {
    private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner = new ThreadLocal<>();
    public static TestNGCucumberRunner getRunner(){
        return testNGCucumberRunner.get();
    }

    private static void setRunner(TestNGCucumberRunner testNGCucumberRunner1)
    {
        testNGCucumberRunner.set(testNGCucumberRunner1);
    }

    BaseMobileTest baseUITest = new BaseMobileTest();
    @Parameters({"environment"})
    @BeforeSuite
    public void setUpEnvironment(String environment) throws Exception {
        baseUITest.SuiteSetup(environment);
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass()  {
        setRunner(new TestNGCucumberRunner(this.getClass()));
    }

    @BeforeTest
    @Parameters({"platformName","deviceIndex"})
    public void TestSetup(String platform, String deviceIndex) throws Exception {
        baseUITest.TestSetup(platform, deviceIndex);
    }

    @BeforeMethod
    public void CreateTest(Method m) throws Exception {
        baseUITest.CreateTest(m);
    }

    @AfterMethod
    public void TearDown() throws Exception {
        baseUITest.TearDown();
    }

    @AfterTest
    public void TestTeardown() throws Exception {
        System.out.println("Stopping Current Test");
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios",dataProvider = "features")
    public void scenario(PickleWrapper pickleWrapper, FeatureWrapper wrapper){
        getRunner().runScenario(pickleWrapper.getPickle());
        System.out.println("scenario: " + pickleWrapper.getPickle().getName());
        BrowserStackUtils.setSessionTestName(baseUITest.getDriver(),pickleWrapper.getPickle().getName());
    }

    @AfterClass(alwaysRun = true)
    public void Cleanup() throws Exception {
        baseUITest.Cleanup();
        if(testNGCucumberRunner == null)
            return;
        testNGCucumberRunner.remove();
    }
    @AfterSuite
    public void tearDownEnvironment() throws Exception {
        baseUITest.afterSuite();
    }
    @DataProvider(name = "features")
    public Object[][] features(){
        return getRunner().provideScenarios();

    }

}