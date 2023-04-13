package Utils;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class iOSUtilities {
    /**
     * Select from iOS Picker
     * @param element
     * @param value
     */

    public void selectFromPicker(IOSElement element, String value)
    {
        element.sendKeys(value);
    }

    /**
     * Accept Alerts
     * @param driver
     */

    public void acceptAlert(IOSDriver driver)
    {
        driver.switchTo().alert().accept();
    }

    /**
     * Decline or cancel alert
     * @param driver
     */

    public void cancelAlert(IOSDriver driver)
    {
        driver.switchTo().alert().dismiss();
    }

    /**
     * Send text confirmation alert
     * @param driver
     * @param text
     */

    public void sendTextConfirmationAlert(IOSDriver driver, String text)
    {
        driver.switchTo().alert().sendKeys(text);
        driver.switchTo().alert().accept();
    }
}
