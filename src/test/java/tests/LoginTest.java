package tests;

import base.BaseTest;
import io.qameta.allure.*;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;

@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression"},
          description = "Verify successful login with valid credentials")
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isDashboardLoaded(),
                "Dashboard should load after login");
    }

    @Test(groups = {"regression"},
          description = "Verify error message on invalid credentials",
          retryAnalyzer = RetryAnalyzer.class)
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user@test.com", "wrongpassword");

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for invalid credentials");
    }
}