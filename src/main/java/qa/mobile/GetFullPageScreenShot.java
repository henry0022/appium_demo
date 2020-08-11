package qa.mobile;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class GetFullPageScreenShot {

    public static String capture(WebDriver driver, String screenShotName) throws Exception
    {
        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") + "/ErrorScreenshots/" + screenShotName + ".png";
        BufferedImage img = ImageIO.read(srcFile);
        ImageIO.write(img,"PNG",new File(dest));
        return dest;
    }
}
