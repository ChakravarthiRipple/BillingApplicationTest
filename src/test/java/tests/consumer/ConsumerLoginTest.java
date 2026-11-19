package tests.consumer;

import base.BaseTest;
import constants.UserRole;
import io.qameta.allure.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;

import java.time.Duration;

@Epic("Authentication")
@Feature("Consumer Login")
public class ConsumerLoginTest extends BaseTest {

    @DataProvider(name = "consumerRoles")
    public Object[][] consumerRoles() {
        return new Object[][] {
            { UserRole.CONSUMER_PREPAID },
            { UserRole.CONSUMER_POSTPAID }
        };
    }

    // ---------- UI (role independent) ----------

    @Test(groups = { "smoke", "regression" }, description = "Login page elements are displayed")
    @Story("Login Page UI")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginPageElementsDisplayed() {
        LoginPage p = new LoginPage(driver);
        Assert.assertTrue(p.isOnLoginPage(), "Should be on login page");
        Assert.assertTrue(p.isLogoDisplayed(), "Logo");
        Assert.assertTrue(p.isHeadingDisplayed(), "Heading");
        Assert.assertTrue(p.isSubHeadingDisplayed(), "Sub heading");
        Assert.assertTrue(p.isEmailFieldDisplayed(), "Email field");
        Assert.assertTrue(p.isPasswordFieldDisplayed(), "Password field");
        Assert.assertTrue(p.isPasswordEyeIconDisplayed(), "Eye icon");
        Assert.assertTrue(p.isOtpCheckboxDisplayed(), "OTP checkbox");
        Assert.assertTrue(p.isLoginButtonDisplayed(), "Login button");
        Assert.assertTrue(p.isForgotPasswordLinkDisplayed(), "Forgot password link");
    }

    @Test(groups = { "regression" }, description = "Both fields empty")
    @Story("Empty Login")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithBothFieldsEmpty() {
        LoginPage p = new LoginPage(driver);
        p.clickLoginWithoutCredentials();
        Assert.assertTrue(p.isEmailFieldErrorDisplayed(), "Email error");
        Assert.assertTrue(p.isPasswordFieldErrorDisplayed(), "Password error");
    }

    @Test(groups = { "smoke", "regression" }, description = "Forgot password link navigates away")
    @Story("Forgot Password")
    @Severity(SeverityLevel.CRITICAL)
    public void testForgotPasswordLinkNavigation() {
        LoginPage p = new LoginPage(driver);
        p.clickForgotPassword();
        Assert.assertFalse(p.isOnLoginPage(), "Should leave the login page");
    }

    // ---------- Per role: Consumer Prepaid and Consumer Postpaid ----------

    @Test(dataProvider = "consumerRoles", groups = { "smoke", "regression" },
            description = "Consumer valid login")
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    public void testConsumerValidLogin(UserRole role) {
        LoginPage p = new LoginPage(driver);
        p.loginAs(role);
        Assert.assertTrue(p.isDashboardLoaded(role), role + " dashboard should load");
    }

    @Test(dataProvider = "consumerRoles", groups = { "regression" },
            description = "Wrong password shows popup and error above email")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidPassword(UserRole role) {
        LoginPage p = new LoginPage(driver);
        p.loginWithInvalidPassword(ConfigReader.getUsername(role), "WrongPassword@999");
        Assert.assertTrue(p.isErrorPopupDisplayed(), "Error popup should show for " + role);
        p.clickOkOnErrorPopup();
        Assert.assertTrue(p.isAboveUsernameErrorDisplayed(), "Error above email should show");
        Assert.assertTrue(p.isOnLoginPage(), "Should stay on login page");
    }

    @Test(dataProvider = "consumerRoles", groups = { "regression" },
            description = "Unknown email cannot log in")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidEmail(UserRole role) {
        LoginPage p = new LoginPage(driver);
        p.login("notregistered.user@gmail.com", ConfigReader.getPassword(role));
        Assert.assertTrue(p.isErrorPopupDisplayed(), "Error popup should show");
        p.clickOkOnErrorPopup();
        Assert.assertTrue(p.isOnLoginPage(), "Should stay on login page");
    }

    @Test(dataProvider = "consumerRoles", groups = { "regression" },
            description = "Password empty")
    @Story("Empty Login")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyPassword(UserRole role) {
        LoginPage p = new LoginPage(driver);
        p.login(ConfigReader.getUsername(role), "");
        Assert.assertTrue(p.isPasswordFieldErrorDisplayed(), "Password error");
    }

    @Test(dataProvider = "consumerRoles", groups = { "regression" },
            description = "Email empty")
    @Story("Empty Login")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyEmail(UserRole role) {
        LoginPage p = new LoginPage(driver);
        p.login("", ConfigReader.getPassword(role));
        Assert.assertTrue(p.isEmailFieldErrorDisplayed(), "Email error");
    }

    @Test(dataProvider = "consumerRoles", groups = { "regression" },
            description = "Eye icon shows and hides the password")
    @Story("Password Visibility")
    @Severity(SeverityLevel.NORMAL)
    public void testPasswordEyeIconToggle(UserRole role) {
        LoginPage p = new LoginPage(driver);
        p.enterPassword(ConfigReader.getPassword(role));
        Assert.assertTrue(p.isPasswordMasked(), "Masked by default");
        p.clickPasswordEyeIcon();
        Assert.assertFalse(p.isPasswordMasked(), "Visible after first click");
        p.clickPasswordEyeIcon();
        Assert.assertTrue(p.isPasswordMasked(), "Masked after second click");
    }

    // Sends a real OTP to each consumer mailbox on every run
    @Test(dataProvider = "consumerRoles", groups = { "regression" },
            description = "Invalid OTP shows popup and error")
    @Story("OTP Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWithInvalidOtp(UserRole role) {
        LoginPage p = new LoginPage(driver);
        p.selectLoginWithOtp(ConfigReader.getUsername(role));
        Assert.assertTrue(p.isOtpFieldDisplayed(), "OTP field should appear");
        p.enterOtpAndLogin("000000");
        Assert.assertTrue(p.isInvalidOtpPopupDisplayed(), "Invalid OTP popup");
        p.clickInvalidOtpPopupOk();
        Assert.assertTrue(p.isInvalidOtpMessageDisplayedOnUsername(), "Invalid OTP message");
    }

    @Test(dataProvider = "consumerRoles", groups = { "regression" },
            description = "Consumer dashboard URL without login redirects to login")
    @Story("Session Protection")
    @Severity(SeverityLevel.CRITICAL)
    public void testDirectDashboardAccessWithoutLogin(UserRole role) {
        driver.get(ConfigReader.getBaseUrl() + ConfigReader.getDashboardPath(role));
        boolean onLogin;
        try {
            onLogin = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains("/auth/login"));
        } catch (Exception e) {
            onLogin = false;
        }
        Assert.assertTrue(onLogin, "Unauthenticated access should redirect to login");
    }
}