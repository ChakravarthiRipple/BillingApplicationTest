package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;
import utils.DriverFactory;
import utils.ScreenshotUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportListener - Auto-generates HTML report with role info and screenshots.
 */
public class ExtentReportListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports getInstance() { return extent; }
    public static ExtentTest      getTest()   { return test.get(); }

    @Override
    public void onStart(ISuite suite) {
        String timestamp   = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath  = "test-output/ExtentReport_" + timestamp + ".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Role-Based Automation Report");
        spark.config().setReportName("Selenium Framework - All Roles");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Application", "Role-Based Web App");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @Override public void onFinish(ISuite suite) { if (extent != null) extent.flush(); }

    @Override
    public void onTestStart(ITestResult result) {
        String role = result.getMethod().getGroups().length > 0
            ? result.getMethod().getGroups()[0] : "general";
        ExtentTest extTest = extent.createTest(
            "[" + role.toUpperCase() + "] " + result.getMethod().getMethodName(),
            result.getMethod().getDescription());
        test.set(extTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, result.getThrowable());
        var driver = DriverFactory.getDriver();
        if (driver != null) {
            test.get().addScreenCaptureFromBase64String(
                ScreenshotUtil.captureBase64(driver), "Failure Screenshot");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "SKIPPED");
    }
}
