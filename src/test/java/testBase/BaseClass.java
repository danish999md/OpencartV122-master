package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

//import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {

    public static WebDriver driver;

    public Logger logger;

    public Properties p;

    public boolean isHeadless() {

        return Boolean.parseBoolean(
                p.getProperty("headless"));
    }
    
    @BeforeClass(groups = {"sanity","regression","master"})
    @Parameters({"os","browser"})

    public void setup(String os, String br)
            throws IOException {

        // Load config.properties

        FileReader file =
                new FileReader(
                        "./src/test/resources/config.properties");

        p = new Properties();

        p.load(file);

        // Log4j

        logger =
                LogManager.getLogger(this.getClass());

        // =========================
        // REMOTE EXECUTION
        // =========================

        if (p.getProperty("execution_env")
                .equalsIgnoreCase("remote")) {

            DesiredCapabilities capabilities =new DesiredCapabilities();

            // OS

            if (os.equalsIgnoreCase("windows")) {

                capabilities.setPlatform(Platform.WIN11);

            } else if (os.equalsIgnoreCase("linux")) {

                capabilities.setPlatform(Platform.LINUX);

            } else if (os.equalsIgnoreCase("mac")) {

                capabilities.setPlatform(Platform.MAC);

            } else {

                System.out.println("Invalid OS");

                return;
            }

            // Browser

            switch (br.toLowerCase()) {

            case "chrome":

                ChromeOptions chromeOptions =new ChromeOptions();
                if (Boolean.parseBoolean(p.getProperty("headless")))
                {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                
                chromeOptions.merge(capabilities);
                capabilities.setBrowserName("chrome");
                driver =new RemoteWebDriver(URI.create("http://localhost:4444").toURL(),chromeOptions);
                break;

            case "edge":

                EdgeOptions edgeOptions =new EdgeOptions();
                if (Boolean.parseBoolean(p.getProperty("headless"))) 
                {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--window-size=1920,1080");
                }

                edgeOptions.merge(capabilities);
                capabilities.setBrowserName("MicrosoftEdge");
                driver =new RemoteWebDriver(URI.create("http://localhost:4444").toURL(),edgeOptions);
                break;

            case "firefox":

                FirefoxOptions firefoxOptions =new FirefoxOptions();
                if (Boolean.parseBoolean(p.getProperty("headless"))) 
                {
                    firefoxOptions.addArguments("--headless");
                }

                firefoxOptions.merge(capabilities);
                capabilities.setBrowserName("firefox");
                driver =new RemoteWebDriver(URI.create("http://localhost:4444").toURL(),firefoxOptions);
                break;

            default:

                System.out.println("Invalid Browser");
                return;
        }
    }

        // =========================
        // LOCAL EXECUTION
        // =========================

        if (p.getProperty("execution_env")
                .equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) {

                case "chrome":

                    BaseClass.driver =
                            new ChromeDriver();

                    break;

                case "edge":

                    WebDriverManager.edgedriver().setup();

                    driver = new EdgeDriver();

                    break;

                case "firefox":

                    BaseClass.driver =
                            new FirefoxDriver();

                    break;

                default:

                    System.out.println(
                            "Invalid Browser");

                    return;
            }
        }

        // =========================
        // COMMON SETUP
        // =========================

        driver.manage().deleteAllCookies();

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        driver.get(p.getProperty("appURL2"));

        driver.manage().window().maximize();

        logger.info("Application launched");
    }

    // =========================
    // SCREENSHOT METHOD
    // =========================

    public static String captureScreen(String testName)
            throws IOException {

        if (driver == null) {

            System.out.println("Driver is null. Screenshot not captured.");

            return null;
        }

        String timeStamp =
                new SimpleDateFormat("yyyyMMddhhmmss")
                        .format(new Date());

        TakesScreenshot ts =
                (TakesScreenshot) driver;

        File sourceFile =
                ts.getScreenshotAs(OutputType.FILE);

        String targetFilePath =
                System.getProperty("user.dir")
                        + "\\screenshots\\"
                        + testName
                        + "_"
                        + timeStamp
                        + ".png";

        File targetFile =
                new File(targetFilePath);

        FileUtils.copyFile(sourceFile, targetFile);

        return targetFilePath;
    }

    // =========================
    // RANDOM STRING
    // =========================

    public String randomeString() {

        return RandomStringUtils.randomAlphabetic(5);
    }

    // =========================
    // RANDOM NUMBER
    // =========================

    public String randomeNumber() {

        return RandomStringUtils.randomNumeric(10);
    }

    // =========================
    // RANDOM ALPHANUMERIC
    // =========================

    public String randomeAlphaNumberic() {

        String generatedString =
                RandomStringUtils.randomAlphabetic(3);

        String generatedNumber =
                RandomStringUtils.randomNumeric(3);

        return generatedString
                + "@"
                + generatedNumber;
    }

    // =========================
    // TEARDOWN
    // =========================

    @AfterMethod(groups = {"sanity","regression","master"})

    public void tearDown() {

        if(driver != null) {

            driver.quit();

            logger.info("Browser closed");
        }
    }
}