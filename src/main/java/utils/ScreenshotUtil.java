package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ScreenshotUtil {
    private static final Logger log = LogManager.getLogger(ScreenshotUtil.class);
    private static final String DIR = "test-output/screenshots/";

    public static String capture(WebDriver driver, String testName) {
        String path = DIR + testName + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            new File(DIR).mkdirs();
            Files.copy(src.toPath(), new File(path).toPath());
            log.info("Screenshot: {}", path);
        } catch (IOException e) {
            log.error("Screenshot failed: {}", e.getMessage());
        }
        return path;
    }

    public static String captureBase64(WebDriver driver) {
        return Base64.getEncoder().encodeToString(
            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
    }
}
