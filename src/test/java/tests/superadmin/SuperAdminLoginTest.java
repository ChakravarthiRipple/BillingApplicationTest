package tests.superadmin;

import base.BaseTest;
import constants.UserRole;
import io.qameta.allure.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;

import java.time.Duration;

@Epic("Authentication")
@Feature("Super Admin Login")
public class SuperAdminLoginTest extends BaseTest {

    private static final UserRole ROLE = UserRole.SUPER_ADMIN;

    private String username() { return ConfigReader.getUsername(ROLE); }
    private String password() { return ConfigReader.getPassword(ROLE); }

    // ---------- UI ----------

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

    @Test(groups = { "regression" }, description = "Eye icon shows and hides the password")
    @Story("Password Visibility")
    @Severity(SeverityLevel.NORMAL)
    public void testPasswordEyeIconToggle() {
        LoginPage p = new LoginPage(driver);
        p.enterPassword(password());
        Assert.assertTrue(p.isPasswordMasked(), "Masked by default");
        p.clickPasswordEyeIcon();
        Assert.assertFalse(p.isPasswordMasked(), "Visible after first click");
        p.clickPasswordEyeIcon();
        Assert.assertTrue(p.isPasswordMasked(), "Masked after second click");
    }

    // ---------- Valid login ----------

    @Test(groups = { "smoke", "regression" }, description = "Super Admin valid login")
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuperAdminValidLogin() {
        LoginPage p = new LoginPage(driver);
        p.login(username(), password());
        Assert.assertTrue(p.isDashboardLoaded(ROLE), "Super Admin dashboard should load");
    }

    // ---------- Invalid login ----------

    @Test(groups = { "regression" }, description = "Wrong password shows popup and error above email")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidPassword() {
        LoginPage p = new LoginPage(driver);
        p.loginWithInvalidPassword(username(), "WrongPassword@999");
        Assert.assertTrue(p.isErrorPopupDisplayed(), "Error popup should show");
        p.clickOkOnErrorPopup();
        Assert.assertTrue(p.isAboveUsernameErrorDisplayed(), "Error above email should show");
        Assert.assertTrue(p.isOnLoginPage(), "Should stay on login page");
    }

    @Test(groups = { "regression" }, description = "Unknown email cannot log in")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidEmail() {
        LoginPage p = new LoginPage(driver);
        p.login("notregistered.user@gmail.com", password());
        Assert.assertTrue(p.isErrorPopupDisplayed(), "Error popup should show");
        p.clickOkOnErrorPopup();
        Assert.assertTrue(p.isOnLoginPage(), "Should stay on login page");
    }

    // ---------- Field validation ----------

    @Test(groups = { "regression" }, description = "Both fields empty")
    @Story("Empty Login")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithBothFieldsEmpty() {
        LoginPage p = new LoginPage(driver);
        p.clickLoginWithoutCredentials();
        Assert.assertTrue(p.isEmailFieldErrorDisplayed(), "Email error");
        Assert.assertTrue(p.isPasswordFieldErrorDisplayed(), "Password error");
    }

    @Test(groups = { "regression" }, description = "Password empty")
    @Story("Empty Login")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyPassword() {
        LoginPage p = new LoginPage(driver);
        p.login(username(), "");
        Assert.assertTrue(p.isPasswordFieldErrorDisplayed(), "Password error");
    }

    @Test(groups = { "regression" }, description = "Email empty")
    @Story("Empty Login")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithEmptyEmail() {
        LoginPage p = new LoginPage(driver);
        p.login("", password());
        Assert.assertTrue(p.isEmailFieldErrorDisplayed(), "Email error");
    }

    // ---------- OTP (sends a real OTP) ----------

    @Test(groups = { "regression" }, description = "Invalid OTP shows popup and error")
    @Story("OTP Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWithInvalidOtp() {
        LoginPage p = new LoginPage(driver);
        p.selectLoginWithOtp(username());
        Assert.assertTrue(p.isOtpFieldDisplayed(), "OTP field should appear");
        p.enterOtpAndLogin("000000");
        Assert.assertTrue(p.isInvalidOtpPopupDisplayed(), "Invalid OTP popup");
        p.clickInvalidOtpPopupOk();
        Assert.assertTrue(p.isInvalidOtpMessageDisplayedOnUsername(), "Invalid OTP message");
    }

    // ---------- Forgot password link ----------

    @Test(groups = { "smoke", "regression" }, description = "Forgot password link navigates away")
    @Story("Forgot Password")
    @Severity(SeverityLevel.CRITICAL)
    public void testForgotPasswordLinkNavigation() {
        LoginPage p = new LoginPage(driver);
        p.clickForgotPassword();
        Assert.assertFalse(p.isOnLoginPage(), "Should leave the login page");
    }

    // ---------- Session protection ----------

    @Test(groups = { "regression" }, description = "Dashboard URL without login redirects to login")
    @Story("Session Protection")
    @Severity(SeverityLevel.CRITICAL)
    public void testDirectDashboardAccessWithoutLogin() {
        driver.get(ConfigReader.getBaseUrl() + ConfigReader.getDashboardPath(ROLE));
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