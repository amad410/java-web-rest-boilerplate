package Reports;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.HashMap;
import java.util.Map;

public class Report {
    private static ExtentReports _report;
    private static ExtentSparkReporter _spark;
    private static Map<Integer, ExtentTest> _extentMap = new HashMap();

    public synchronized static ExtentReports getReporter(){
        if(_report == null)
            _report = new ExtentReports();
        _spark = new ExtentSparkReporter("target/Report.html");
        _spark.config().setDocumentTitle("Mobile Automation Tests");
        _spark.config().setReportName("Mobile Test Results");
        _spark.config().setTheme(Theme.STANDARD);
        _report.attachReporter(_spark);
        return _report;
    }
    public static synchronized  ExtentTest getTest(){
        return (ExtentTest) _extentMap.get((int) (long) (Thread.currentThread().getId()));
    }
    public static synchronized  ExtentTest startTest(String testName, String description){
        ExtentTest test = getReporter().createTest(testName, description);
        _extentMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
}
