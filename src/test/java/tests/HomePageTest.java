package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;

@Epic("Dashboard")
@Feature("Home Page")
public class HomePageTest extends BaseTest {

    @BeforeMethod
    public void loginBeforeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    @Test(groups = {"smoke"}, description = "Verify home page loads after login")
    @Story("Page Load")
    @Severity(SeverityLevel.CRITICAL)
    public void testHomePageIsLoaded() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isDashboardLoaded(),
                "Home page should be displayed after login");
    }

    @Test(groups = {"regression"}, description = "Verify user profile is visible on home page")
    @Story("User Profile")
    @Severity(SeverityLevel.NORMAL)
    public void testUserProfileVisible() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isUserProfileVisible(),
                "User profile should be visible on home page");
    }

    @Test(groups = {"regression"}, description = "Verify logout redirects to login page")
    @Story("Logout")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogout() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"),
                "Should redirect to login after logout");
    }
}