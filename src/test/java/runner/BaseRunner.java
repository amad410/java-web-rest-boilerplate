package runner;

import base.BaseAPITest;

import io.cucumber.testng.TestNGCucumberRunner;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;

import org.testng.annotations.*;
import java.lang.reflect.Method;

public class BaseRunner {

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
    public void setUpClass() throws Exception {
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
