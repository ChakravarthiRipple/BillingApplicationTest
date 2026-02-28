package tests;

import base.BaseTest;
import constants.UserRole;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;

@Epic("Dashboard")
@Feature("Home Page - All Roles")
public class HomePageTest extends BaseTest {

	// ──────────────────────────────────────────────────────────
	// SCENARIO 1: Welcome message for Super Admin
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "smoke", "super_admin" }, description = "Super Admin sees Welcome To Ripple Dashboard!")
	@Story("Dashboard Load")
	@Severity(SeverityLevel.BLOCKER)
	public void testSuperAdminDashboard() {
		new LoginPage(driver).loginAs(UserRole.SUPER_ADMIN);
		HomePage home = new HomePage(driver);
		Assert.assertTrue(home.isDashboardLoaded(), "Dashboard should load for Super Admin");
		Assert.assertTrue(home.getWelcomeMessage().contains("Welcome To Ripple Dashboard!"),
				"Welcome message should be displayed");
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 2: Welcome message for Super User
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "smoke", "super_user" }, description = "Super User sees Welcome To Ripple Dashboard!")
	@Story("Dashboard Load")
	@Severity(SeverityLevel.BLOCKER)
	public void testSuperUserDashboard() {
		new LoginPage(driver).loginAs(UserRole.SUPER_USER);
		HomePage home = new HomePage(driver);
		Assert.assertTrue(home.isDashboardLoaded(), "Dashboard should load for Super User");
		Assert.assertTrue(home.getWelcomeMessage().contains("Welcome To Ripple Dashboard!"),
				"Welcome message should be displayed");
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 3: Welcome message for Admin Prepaid
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "smoke", "admin_prepaid" }, description = "Admin Prepaid sees Welcome To Ripple Dashboard!")
	@Story("Dashboard Load")
	@Severity(SeverityLevel.BLOCKER)
	public void testAdminPrepaidDashboard() {
		new LoginPage(driver).loginAs(UserRole.ADMIN_PREPAID);
		HomePage home = new HomePage(driver);
		Assert.assertTrue(home.isDashboardLoaded(), "Dashboard should load for Admin Prepaid");
		Assert.assertTrue(home.getWelcomeMessage().contains("WELCOME TO RIPPLE DASHBOARD!"),
				"Welcome message should be displayed");
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 4: Welcome message for Admin Postpaid
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "smoke", "admin_postpaid" }, description = "Admin Postpaid sees Welcome To Ripple Dashboard!")
	@Story("Dashboard Load")
	@Severity(SeverityLevel.BLOCKER)
	public void testAdminPostpaidDashboard() {
		new LoginPage(driver).loginAs(UserRole.ADMIN_POSTPAID);
		HomePage home = new HomePage(driver);
		Assert.assertTrue(home.isDashboardLoaded(), "Dashboard should load for Admin Postpaid");
		Assert.assertTrue(home.getWelcomeMessage().contains("WELCOME TO RIPPLE DASHBOARD!"),
				"Welcome message should be displayed");
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 5: Welcome message for Consumer Prepaid
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "smoke", "consumer_prepaid" }, description = "Consumer Prepaid sees Welcome To Ripple Dashboard!")
	@Story("Dashboard Load")
	@Severity(SeverityLevel.BLOCKER)
	public void testConsumerPrepaidDashboard() {
		new LoginPage(driver).loginAs(UserRole.CONSUMER_PREPAID);
		HomePage home = new HomePage(driver);
		Assert.assertTrue(home.isDashboardLoaded(), "Dashboard should load for Consumer Prepaid");
		Assert.assertTrue(home.getWelcomeMessage().contains("WELCOME TO RIPPLE DASHBOARD!"),
				"Welcome message should be displayed");
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 6: Welcome message for Consumer Postpaid
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "smoke",
			"consumer_postpaid" }, description = "Consumer Postpaid sees Welcome To Ripple Dashboard!")
	@Story("Dashboard Load")
	@Severity(SeverityLevel.BLOCKER)
	public void testConsumerPostpaidDashboard() {
		new LoginPage(driver).loginAs(UserRole.CONSUMER_POSTPAID);
		HomePage home = new HomePage(driver);
		Assert.assertTrue(home.isDashboardLoaded(), "Dashboard should load for Consumer Postpaid");
		Assert.assertTrue(home.getWelcomeMessage().contains("WELCOME TO RIPPLE DASHBOARD!"),
				"Welcome message should be displayed");
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 7: Profile icon click - dropdown displayed
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "regression" }, description = "Click profile icon - dropdown menu is displayed")
	@Story("Profile Dropdown")
	@Severity(SeverityLevel.NORMAL)
	public void testProfileDropdownDisplayed() {
		new LoginPage(driver).login(ConfigReader.getUsername(), ConfigReader.getPassword());
		HomePage home = new HomePage(driver);
		home.clickProfileIcon();
		Assert.assertTrue(home.isProfileDropdownDisplayed(),
				"Profile dropdown should be displayed after clicking profile icon");
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 8: Profile popup - all fields displayed
	// ──────────────────────────────────────────────────────────
	@Test(groups = {
			"regression" }, description = "Click Profile in dropdown - profile popup with all fields displayed")
	@Story("Profile Popup")
	@Severity(SeverityLevel.CRITICAL)
	public void testProfilePopupFields() {
		new LoginPage(driver).login(ConfigReader.getUsername(), ConfigReader.getPassword());
		HomePage home = new HomePage(driver);

		// Open profile popup
		home.clickProfileIcon();
		home.clickProfileMenuItem();

		// Verify popup is displayed
		Assert.assertTrue(home.isProfilePopupDisplayed(), "Profile popup should be displayed");

		// Verify all fields are present
		Assert.assertTrue(home.isFirstNameFieldDisplayed(), "First Name field should be displayed");
		Assert.assertTrue(home.isLastNameFieldDisplayed(), "Last Name field should be displayed");
		Assert.assertTrue(home.isEmailFieldDisplayed(), "Email Address field should be displayed");
		Assert.assertTrue(home.isPrimaryMobileFieldDisplayed(), "Primary Mobile field should be displayed");
		Assert.assertTrue(home.isSecondaryEmailFieldDisplayed(), "Secondary Email field should be displayed");
		Assert.assertTrue(home.isSecondaryMobileFieldDisplayed(), "Secondary Mobile field should be displayed");
		Assert.assertTrue(home.isCountryDropdownDisplayed(), "Country dropdown should be displayed");

	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 9: Update profile - success popup displayed
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "regression" }, description = "Update profile and verify success popup, click OK")
	@Story("Profile Update")
	@Severity(SeverityLevel.CRITICAL)
	public void testProfileUpdate() {
		new LoginPage(driver).login(ConfigReader.getUsername(), ConfigReader.getPassword());
		HomePage home = new HomePage(driver);

		// Open profile popup
		home.clickProfileIcon();
		home.clickProfileMenuItem();
		Assert.assertTrue(home.isProfilePopupDisplayed(), "Profile popup should open");

		// Update first name
		home.updateFirstName("Chakravarthi");
		home.updateLastName("App tester");

		// Click Update button
		home.clickUpdate();

		// Verify success popup
		Assert.assertTrue(home.isSuccessPopupDisplayed(), "Success popup should be displayed after update");

		// Click OK
		home.clickOkOnSuccessPopup();
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 10: Full flow - open profile again then logout
	// ──────────────────────────────────────────────────────────
	@Test(groups = { "regression" }, description = "After update - open profile again then logout")
	@Story("Profile then Logout")
	@Severity(SeverityLevel.CRITICAL)
	public void testProfileThenLogout() throws InterruptedException {
		new LoginPage(driver).login(ConfigReader.getUsername(), ConfigReader.getPassword());
		HomePage home = new HomePage(driver);

		// Open profile popup
		home.clickProfileIcon();
		home.clickProfileMenuItem();
		Assert.assertTrue(home.isProfilePopupDisplayed(), "Profile popup should open second time");
		home.clickUpdate();
		home.clickOkOnSuccessPopup();

		// Close popup / go back - then logout
		home.clickProfileIcon();
		home.clickLogoutMenuItem();

		// Verify redirected to login
		Thread.sleep(2000);
		Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Should redirect to login page after logout");
	}
}