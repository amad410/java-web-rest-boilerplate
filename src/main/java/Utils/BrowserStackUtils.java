package Utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;

public class BrowserStackUtils {

    public static void setSessionStatus(AppiumDriver driver, String status, String reason) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(String.format(
                "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"%s\", \"reason\": \"%s\"}}",
                status, reason));
    }

    public static void setSessionTestName(AppiumDriver driver, String name) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(String.format("browserstack_executor: {\"action\": \"setSessionName\", \"arguments\": {\"name\":\"%s\" }}",name));
    }
}
