package qa.mobile;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AppiumDemo {

    static AppiumDriver driver;
    static int ssindex;

    static ExtentHtmlReporter htmlReporter;
    static ExtentReports extent;
    static ExtentTest currentTest;


    @BeforeClass
    private  void beforeClass() throws MalformedURLException {
        setupExtentReport();
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
        URL url = new URL(URL_STRING);
        File app = new File("android-ump-mobile-debug-67a35ff.apk");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2_API_30");
        caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        driver = new AndroidDriver(url, caps);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void checkGenres() {

        setCurrentTest("checkGenres", "Checks that all three genres are displayed");
        clickElementById(homePage.genresButton);
        //checking genres:
        validateElementVisibilityByText(genresPage.rockDescription_text);
        validateElementVisibilityByText(genresPage.cinematicTitle_text);
        validateElementVisibilityByText(genresPage.cinematicDescription_text);
        validateElementVisibilityByText(genresPage.jazzAndBluesTitle_text);
        validateElementVisibilityByText(genresPage.jazzAndBluesDescription_text);
    }
    @Test
    public void playRock() {

        setCurrentTest("Play_Rock", "Checks that the user can start a song under the rock genre");
        clickElementByText(genresPage.rockTitle_text);
        clickElementByText("Awakening");
        clickElementByText("Awakening");
    }




    private static void clickElementById(String id) {
        try{
            driver.findElementById(id).click();

            currentTest.pass("Successfully clicked element by id: " + id,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath("-CLICK-SUCCESS: " + id )).build());


        }catch (Exception e){
            Assert.fail("Failed to validate that element by id: " + id + " was displayed/clickable");

            try {
                currentTest.fail("Failed to click element by id: " + id,
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath("-CLICK-FAILURE: " + id )).build());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    private static void validateElementVisibilityByText(String text) {
        try{

            currentTest.pass("Successfully clicked element by text: " + text,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath("-CLICK-SUCCESS: " + text )).build());

            driver.findElementByXPath("//*[@text = '"+text+"']").isDisplayed();
        }catch (Exception e){
            Assert.fail("Failed to validate that element by text: " + text + " was displayed");

            try {
                currentTest.fail("Failed to click element by text " + text,
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath("-CLICK-FAILURE: " + text )).build());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    private void clickElementByText(String text) {
        try{
               List<WebElement> elements = driver.findElementsByXPath("//*[@text = '"+text+"']") ;
                WebElement el = elements.get(elements.size()-1);
                el.click();
                Thread.sleep(400);
            currentTest.pass("Successfully clicked element by text: " + text,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath("-CLICK-SUCCESS: " + text )).build());


        }catch (Exception e){
            Assert.fail("Failed to validate that element by text: " + text + " was displayed/clickable");
            try {
                currentTest.fail("Failed to click: by id " + text,
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath("-CLICK-FAILURE: " + text )).build());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }


    private static void setupExtentReport() {
        htmlReporter = new ExtentHtmlReporter("index.html");
        extent  = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    private static void setCurrentTest(String testName, String description) {
        currentTest = extent.createTest(testName, description);
    }


    private static void log(String action) {
        currentTest.log(Status.INFO,action);
    }

    private static String screenshotPath(String name) {
        try {
            ssindex++;
            String screenShotPath = GetFullPageScreenShot.capture(driver, ""+ssindex);
            return screenShotPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @AfterClass

    private static void afterClass() {
        ((AppiumDriver)driver).closeApp();
        ((AppiumDriver)driver).quit();


        extent.flush();
    }

}
