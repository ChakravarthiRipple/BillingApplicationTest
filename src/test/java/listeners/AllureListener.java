package listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.DriverFactory;
import utils.ScreenshotUtil;

import java.io.ByteArrayInputStream;

public class AllureListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Allure.label("browser", "Chrome");
        Allure.label("host",    "billing.ripplemetering.com");
        Allure.label("role",    getRoleFromGroups(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        attachScreenshot("PASS - " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot("FAIL - " + result.getMethod().getMethodName());

        Allure.addAttachment("Failure Reason",
                "text/plain",
                result.getThrowable().getMessage());

        StringBuilder sb = new StringBuilder();
        for (StackTraceElement el : result.getThrowable().getStackTrace()) {
            sb.append(el.toString()).append("\n");
        }
        Allure.addAttachment("Stack Trace", "text/plain", sb.toString());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        attachScreenshot("SKIP - " + result.getMethod().getMethodName());
    }

    private void attachScreenshot(String name) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            try {
                Allure.addAttachment(
                    name,
                    "image/png",
                    new ByteArrayInputStream(ScreenshotUtil.captureBytes(driver)),
                    ".png"
                );
            } catch (Exception e) {
                Allure.addAttachment("Screenshot Error",
                        "text/plain", e.getMessage());
            }
        }
    }

    private String getRoleFromGroups(ITestResult result) {
        for (String g : result.getMethod().getGroups()) {
            if (!g.equals("smoke") && !g.equals("regression")) {
                return g.replace("_", " ").toUpperCase();
            }
        }
        return "GENERAL";
    }
}