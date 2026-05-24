package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

	public static String capture(WebDriver driver, String name) throws IOException {
		
		if (driver == null) {
	        System.out.println("Driver is null, screenshot skipped");
	        return null;
	    }

	    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        String path = System.getProperty("user.dir") + "\\screenshots\\" + name + timeStamp + ".png";
        File dest = new File(path);

        Files.copy(src.toPath(), dest.toPath());

        return path;
    }
	
}
