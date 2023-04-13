package Utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AndroidUtilities {


    /**
     * This method can only be used for Android emulators
     * @param driver
     */
    public void sendSMS(AndroidDriver driver, String phoneNumber, String message)
    {
        driver.sendSMS(phoneNumber, message);
    }

    /**
     * Set and paste text from Clipboard
     * @param driver
     * @param element
     * @param text
     */

    public void pasteFromClipBoard(AndroidDriver driver, MobileElement element, String text )
    {
        driver.setClipboardText(text);
        element.clear();
        element.sendKeys(driver.getClipboardText());
    }
}
