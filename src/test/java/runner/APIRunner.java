package runner;

import Utils.ConfigLoader;
import base.BaseAPITest;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import com.cucumber.listener.Reporter;
import io.cucumber.testng.*;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;


@CucumberOptions(features="src/test/resources/features",
        glue = "sampleSteps",
        monochrome = true,
        plugin = {"pretty",
                "html:target/cucumber-html-report.html","json:target/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        tags = "@API")
@Listeners({ExtentITestListenerClassAdapter.class})
public class APIRunner extends AbstractTestNGCucumberTests {

    private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner = new ThreadLocal<>();
    public static TestNGCucumberRunner getRunner(){
        return testNGCucumberRunner.get();
    }

    private static void setRunner(TestNGCucumberRunner testNGCucumberRunner1)
    {
        testNGCucumberRunner.set(testNGCucumberRunner1);
    }

    BaseAPITest baseAPITest = new BaseAPITest();

    @BeforeSuite
    @Parameters({"environment"})
    public void setUpEnvironment(String environment) throws Exception {
        baseAPITest.SuiteSetup(environment);
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        setRunner(new TestNGCucumberRunner(this.getClass()));
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios",dataProvider = "features")
    public void scenario(PickleWrapper pickleWrapper, FeatureWrapper wrapper){
        getRunner().runScenario(pickleWrapper.getPickle());
    }

    @BeforeTest
    public void TestSetup() throws Exception {
        baseAPITest.TestSetup();
    }

    @BeforeMethod
    public void CreateTest(Method m) throws Exception {
        baseAPITest.CreateTest(m);
    }

    @AfterMethod
    public void TearDown() throws Exception {
        baseAPITest.TearDown();

    }

    @AfterTest
    public void TestTeardown() throws Exception {
        baseAPITest.TestTeardown();
    }


    @AfterClass(alwaysRun = true)
    public void Cleanup() throws Exception {
        baseAPITest.Cleanup();
        if(testNGCucumberRunner == null)
            return;
        testNGCucumberRunner.remove();
    }

    @DataProvider(name = "features")
    public Object[][] features(){
        return getRunner().provideScenarios();

    }
}
