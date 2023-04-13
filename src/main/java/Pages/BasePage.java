package Pages;

import Handlers.DriverManager;
import Reports.Report;
import Utils.WaitUtils;
import com.aventstack.extentreports.Status;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Set;

import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public class BasePage {
    AppiumDriver _driver;
    public AndroidTouchAction actions;

    public BasePage(){
        this._driver = new DriverManager().getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(this._driver),this);

    }

    public void switchToWebView(){
        Set<String> contexts = _driver.getContextHandles();
        for (String context: contexts){
            if (context.contains("WEBVIEW")){
                _driver.context(context);
                break;
            }
        }
    }

    public void clear(MobileElement element, long timeOut){
        WaitUtils.waitForVisibility(element,_driver,timeOut);
        element.clear();
    }

    public void clear(By e, long timeOut){
        WebDriverWait wait = new WebDriverWait(_driver,timeOut);
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
        _driver.findElement(e).clear();
    }

    public void click(MobileElement element, long timeOut){
        WaitUtils.waitForVisibility(element,_driver,timeOut);
        element.click();
    }
    public void click(By e, long timeOut){
        WebDriverWait wait = new WebDriverWait(_driver,timeOut);
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
        _driver.findElement(e).click();
    }
    public void sendKeys(By e, String text, long timeOut){
        WebDriverWait wait = new WebDriverWait(_driver,timeOut);
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
        _driver.findElement(e).sendKeys(text);
    }
    public void enterText(MobileElement element, String text, long timeOut){
        WaitUtils.waitForVisibility(element,_driver,timeOut);
        element.clear();
        element.sendKeys(text);
    }

    public String getAttribute(MobileElement element, String attribute, long timeOut){
        WaitUtils.waitForVisibility(element,_driver,timeOut);
        return element.getAttribute(attribute);
    }
    public void getAttribute(By e, String attribute, long timeOut){
        WebDriverWait wait = new WebDriverWait(_driver,timeOut);
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
        _driver.findElement(e).getAttribute(attribute);
    }
    private void scrollDown(double startPosition, double endPosition, long waitDuration){
        Dimension dimension = this._driver.manage().window().getSize();
        int scrollStart = (int) (dimension.getHeight() * startPosition);
        int scrollEnd = (int) (dimension.getHeight() * endPosition);

        actions = new AndroidTouchAction(this._driver)
                .press(point(0, scrollStart))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(waitDuration)))
                .moveTo(point(0, scrollEnd))
                .release()
                .perform();
    }
    public void scrollAndClick(MobileElement elementToClick, double startPosition, double endPosition, long waitDuration){

        actions = new AndroidTouchAction(this._driver);

        //ScrollDown
        scrollDown(startPosition, endPosition, waitDuration);

        actions.tap(ElementOption.element(elementToClick)).perform();
    }
    public void tap(MobileElement element){
        actions = new AndroidTouchAction(this._driver);
        actions.tap(ElementOption.element(element)).perform();
    }
    public void dragDrop(String dragPointId, String dropPointId){

        actions = new AndroidTouchAction(this._driver);

        AndroidElement drag = (AndroidElement) this._driver.findElement(By.id(dragPointId));
        AndroidElement drop = (AndroidElement) this._driver.findElement(By.id(dropPointId));

        actions.longPress(ElementOption.element(drag))
                .waitAction().moveTo(ElementOption.element(drop))
                .release()
                .perform();
    }
    public void swipe(MobileElement startingElem, int xOffset, int yOffset){

        actions = new AndroidTouchAction(this._driver);

        actions.press(ElementOption.element(startingElem))
                .waitAction()
                .moveTo(point(xOffset, yOffset))
                .release()
                .perform();
    }
    public void swipe(int startX, int startY, int endX, int endY, int millis){

        TouchAction t = new TouchAction(_driver);
        t.press(point(startX, startY)).waitAction(WaitOptions.waitOptions(ofMillis(millis))).moveTo(point(endX, endY)).release()
                .perform();
    }

    public String androidGetText(MobileElement e, String msg, long waitDuration) {
        String txt = null;

        txt = getAttribute(e, "text",waitDuration);
        Report.getTest().log(Status.INFO, msg + txt);
        return txt;
    }

    public String iOSGetText(MobileElement e, String msg, long waitDuration) {
        String txt = null;
        txt = getAttribute(e, "label",waitDuration);
        Report.getTest().log(Status.INFO, msg + txt);
        return txt;
    }

    public MobileElement androidScrollToElement() {
        return (MobileElement) ((FindsByAndroidUIAutomator) _driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector().description(\"test-Price\"));");
    }
    public void waitForVisibility(MobileElement e, long timeOut){
        WaitUtils.waitForVisibility(e,_driver,timeOut);
    }
    public void waitForVisibility(By e, long timeOut){
        WebDriverWait wait = new WebDriverWait(_driver,timeOut);
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
        WaitUtils.waitForVisibility(e,_driver,timeOut);
    }
    public void waitForWebDriverVisibility(By e,long timeOut){
        WebDriverWait wait = new WebDriverWait(_driver,timeOut);
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
    }

    public MobileElement iOSScrollTOElementUsingMobileScroll(MobileElement e){

        RemoteWebElement element = ((RemoteWebElement)e );
        String elementId = element.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", elementId);
        scrollObject.put("toVisible","something");
        _driver.executeScript("mobile:scroll", scrollObject);
        return e;
    }

    public By iOSScrollTOElementUsingMobileScrollParent(MobileElement parentE, String predictateString){

        RemoteWebElement element = ((RemoteWebElement)parentE );
        String elementId = element.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", elementId);
        scrollObject.put("predicateString",predictateString);
        _driver.executeScript("mobile:scroll", scrollObject);
        By m = MobileBy.iOSNsPredicateString(predictateString);
        return m;
    }

    public MobileElement scrollToElement(MobileElement e, String direction) throws Exception {
        Dimension size = _driver.manage().window().getSize();
        int startX = (int) (size.width * 0.5);
        int endX = (int) (size.width * 0.5);
        int startY = 0;
        int endY = 0;
        Boolean isFound = false;
        switch(direction){
            case "up":
                endY = (int) (size.height * 0.4);
                startY = (int) (size.height * 0.6);
                break;
            case "down":
                endY = (int) (size.height * 0.6);
                startY = (int) (size.height * 0.4);
                break;
            default:
                throw new Exception("Invalid direction");
        }

        for(int i = 0; i < 3; i++)
        {
            if(find(e, 1)){
                isFound = true;
                break;
            }
            else{
                swipe(startX, startY, endX, endY, 1000);
            }

        }
        if(!isFound)
        {
            throw new Exception("Element not found");
        }
        return e;
    }
    public Boolean find(final MobileElement element, long duration){
        try{
            WebDriverWait wait = new WebDriverWait(_driver, duration);
            return wait.until(new ExpectedCondition<Boolean>(){
                @Override
                public Boolean apply(WebDriver driver){
                    if(element.isDisplayed()){
                        return true;
                    }
                    return false;
                }
            });
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public Boolean find(By element, long duration){
        try{
            WebDriverWait wait = new WebDriverWait(_driver, duration);
            return wait.until(new ExpectedCondition<Boolean>(){
                @Override
                public Boolean apply(WebDriver driver){
                    if(_driver.findElement(element).isDisplayed()){
                        return true;
                    }
                    return false;
                }
            });
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public void closeApplication(){
        ((InteractsWithApps)_driver).closeApp();
    }
    public void launchApplication(){
        ((InteractsWithApps)_driver).launchApp();
    }
}
