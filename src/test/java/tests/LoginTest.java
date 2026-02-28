package tests;

import base.BaseTest;
import constants.UserRole;
import io.qameta.allure.*;
import listeners.RetryAnalyzer;

import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;

@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

	// ──────────────────────────────────────────────────────────
	// SCENARIO 1: Valid login
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "smoke", "regression" }, description = "Valid email and password - should login successfully")
	@Story("Valid Login")
	@Severity(SeverityLevel.BLOCKER)
	public void testValidLogin() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

		DashboardPage dashboard = new DashboardPage(driver);
		Assert.assertTrue(dashboard.isDashboardLoaded(), "Dashboard should load after valid login");
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 2: Invalid password - error popup + error above username
	// ──────────────────────────────────────────────────────────
	@Test(groups = {
			"regression" }, description = "Invalid password - error popup displayed, click OK, error shown above username", retryAnalyzer = RetryAnalyzer.class)
	@Story("Invalid Login")
	@Severity(SeverityLevel.CRITICAL)
	public void testInvalidPasswordShowsErrorPopup() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithInvalidPassword(ConfigReader.getUsername(), "WrongPassword@999");

		// Error popup should appear
		Assert.assertTrue(loginPage.isErrorPopupDisplayed(), "Error popup should be displayed for invalid password");

		// Click OK on popup
		loginPage.clickOkOnErrorPopup();

		// Error message should now appear above username field
		Assert.assertTrue(loginPage.isAboveUsernameErrorDisplayed(),
				"Error should be shown above username field after dismissing popup");

		System.out.println("Error text: " + loginPage.getAboveUsernameErrorText());
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 3: Direct click login without credentials
	// ──────────────────────────────────────────────────────────
	@Test(groups = {
			"regression" }, description = "Click login without entering email or password - inline errors shown")
	@Story("Empty Login")
	@Severity(SeverityLevel.NORMAL)
	public void testLoginWithoutCredentials() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.clickLoginWithoutCredentials();

		Assert.assertTrue(loginPage.isEmailFieldErrorDisplayed(), "Email field error should be displayed");
		Assert.assertTrue(loginPage.isPasswordFieldErrorDisplayed(), "Password field error should be displayed");

		System.out.println("Email error: " + loginPage.getEmailFieldErrorText());
		System.out.println("Password error: " + loginPage.getPasswordFieldErrorText());
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 4A: Login with INVALID OTP
	// ──────────────────────────────────────────────────────────
	@Test(groups = {
			"regression" }, description = "Enter email, login with OTP, enter invalid OTP, verify error popup and message")
	@Story("OTP Login - Invalid OTP")
	@Severity(SeverityLevel.CRITICAL)
	public void testLoginWithInvalidOtp() {

		LoginPage loginPage = new LoginPage(driver);

		// Enter email and click Send OTP
		loginPage.selectLoginWithOtp(ConfigReader.getUsername());

		// Verify OTP field appears
		Assert.assertTrue(loginPage.isOtpFieldDisplayed(), "OTP input field should appear after clicking Send OTP");

		// Enter INVALID OTP automatically
		String invalidOtp = "000000";
		loginPage.enterOtpAndLogin(invalidOtp);

		// Verify Invalid OTP popup is displayed
		Assert.assertTrue(loginPage.isInvalidOtpPopupDisplayed(), "Invalid OTP popup should be displayed");

		// Click OK on popup
		loginPage.clickInvalidOtpPopupOk();

		// Verify error message displayed near username/email field
		Assert.assertTrue(loginPage.isInvalidOtpMessageDisplayedOnUsername(),
				"Invalid OTP message should be displayed near username field");
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 5: Login for all 6 roles
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "smoke" }, description = "Super Admin login")
	@Story("Role Login")
	@Severity(SeverityLevel.BLOCKER)
	public void testSuperAdminLogin() {
		new LoginPage(driver).loginAs(UserRole.SUPER_ADMIN);

		DashboardPage dashboard = new DashboardPage(driver);
		Assert.assertTrue(dashboard.isDashboardLoaded(), "Super Admin dashboard should load");
	}

	@Test(groups = { "smoke" }, description = "Super User login")
	@Story("Role Login")
	@Severity(SeverityLevel.BLOCKER)
	public void testSuperUserLogin() {
		new LoginPage(driver).loginAs(UserRole.SUPER_USER);
		Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Super User dashboard should load");
	}

	@Test(groups = { "smoke" }, description = "Admin Prepaid login")
	@Story("Role Login")
	@Severity(SeverityLevel.BLOCKER)
	public void testAdminPrepaidLogin() {
		new LoginPage(driver).loginAs(UserRole.ADMIN_PREPAID);
		Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Admin Prepaid dashboard should load");
	}

	@Test(groups = { "smoke" }, description = "Admin Postpaid login")
	@Story("Role Login")
	@Severity(SeverityLevel.BLOCKER)
	public void testAdminPostpaidLogin() {
		new LoginPage(driver).loginAs(UserRole.ADMIN_POSTPAID);
		Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Admin Postpaid dashboard should load");
	}

	@Test(groups = { "smoke" }, description = "Consumer Prepaid login")
	@Story("Role Login")
	@Severity(SeverityLevel.BLOCKER)
	public void testConsumerPrepaidLogin() {
		new LoginPage(driver).loginAs(UserRole.CONSUMER_PREPAID);
		Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Consumer Prepaid dashboard should load");
	}

	@Test(groups = { "smoke" }, description = "Consumer Postpaid login")
	@Story("Role Login")
	@Severity(SeverityLevel.BLOCKER)
	public void testConsumerPostpaidLogin() {
		new LoginPage(driver).loginAs(UserRole.CONSUMER_POSTPAID);
		Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Consumer Postpaid dashboard should load");
	}
}