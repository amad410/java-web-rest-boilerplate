package base;

import APIClients.BaseClient;
import Handlers.ConfigHandler;
import org.junit.BeforeClass;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class BaseAPITest  {

    protected String _environment;
    protected ConfigHandler _configHandler;
    private String _baseUri;
    private BaseClient _baseClient;


    public BaseAPITest(){
        _configHandler = new ConfigHandler();
        SetConfigHandler(_configHandler);
        _baseClient = new BaseClient();
    }

    @BeforeClass
    public void ClassSetup(){
        System.out.println("Test Class Setup");

    }

    @BeforeSuite
    @Parameters({"environment"})
    public void SuiteSetup(String environment) throws Exception {
        try{
            System.out.println("Setup Test Suite");
            System.setProperty("environment",environment);
            if(System.getProperty("environment")!=null)
            {
                _environment = environment;
                _configHandler.retrieveEnvConfiguration(_environment);
                _baseUri = _configHandler.GetBaseURI();
                _baseClient.SetBaseURI(_baseUri);

            }
            else if(System.getProperty("environment")==null){
                throw new Exception("No Environment has been set");
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    @BeforeTest
    public void TestSetup() throws Exception {
        System.out.println("Setup Current Test");

    }

    @BeforeMethod
    public void CreateTest(Method m) throws Exception {
        System.out.println("Creating Current Test");
        System.out.println("Starting Test: " + m.getName());
        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }

    @AfterMethod
    public void TearDown() throws Exception {
        System.out.println("Stopping Current Test");

    }

    @AfterTest
    public void TestTeardown() throws Exception {
        System.out.println("Stopping All Tests");
    }


    @AfterClass
    public void Cleanup() throws Exception {
        System.out.println("Test Class Cleanup");

    }

    public void SetConfigHandler(ConfigHandler configHandler){
        _configHandler = configHandler;

    }

    public ConfigHandler GetConfigHandler()
    {
        return _configHandler;
    }
}
