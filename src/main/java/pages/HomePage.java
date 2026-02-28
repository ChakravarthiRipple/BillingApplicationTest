package pages;

import base.BasePage;

import java.time.Duration;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * HomePage - Dashboard page for all 6 roles. All roles see "Welcome To Ripple
 * Dashboard!" after login.
 */
public class HomePage extends BasePage {

	// ── Dashboard Welcome Message ─────────────────────────────
	@FindBy(xpath = "//h4[contains(text(),'Welcome To Ripple Dashboard!')]")
	private WebElement welcomeMessage;

	// ── Profile Icon (top right corner) ──────────────────────
	@FindBy(xpath = "//img[(@class='rounded-circle header-profile-user')]")
	private WebElement profileIcon;

	// ── Profile Dropdown ──────────────────────────────────────
	@FindBy(xpath = "//div[@class='dropdown-menu dropdown-menu-end show']")
	private WebElement profileDropdown;

	@FindBy(xpath = "//a[(@class='dropdown-item')]")
	private WebElement profileMenuItem;

	@FindBy(xpath = "//a[(@class='dropdown-item text-danger')]")
	private WebElement logoutMenuItem;

	// ── Profile Popup Fields ──────────────────────────────────
	@FindBy(xpath = "(//h5[contains(text(),'Profile')])[2]")
	private WebElement profilePopup;

	@FindBy(id = "firstName")
	private WebElement firstNameField;

	@FindBy(id = "lastName")
	private WebElement lastNameField;

	@FindBy(id = "emailAddress")
	private WebElement emailField;

	@FindBy(id = "mobileNumber")
	private WebElement primaryMobileField;

	@FindBy(id = "secondaryEmail")
	private WebElement secondaryEmailField;

	@FindBy(id = "secondaryPhoneNumber")
	private WebElement secondaryMobileField;

	@FindBy(xpath = "//div[@class='ng-select-container']")
	private WebElement countryDropdown;

	// ── Update Button ─────────────────────────────────────────
	@FindBy(xpath = "//button[contains(text(),'Update')]")
	private WebElement updateButton;

	// ── Update Success Popup ──────────────────────────────────
	@FindBy(xpath = "//h2[contains(text(),'Success')]")
	private WebElement successPopup;

	@FindBy(xpath = "//button[contains(text(),'OK')]")
	private WebElement successOkButton;

	public HomePage(WebDriver driver) {
		super(driver);
	}

	// ── Dashboard ─────────────────────────────────────────────

	public boolean isDashboardLoaded() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getWelcomeMessage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
		return welcomeMessage.getText();
	}

	// ── Profile Icon & Dropdown ───────────────────────────────

	public void clickProfileIcon() {
		click(profileIcon);
	}

	public boolean isProfileDropdownDisplayed() {
		return isDisplayed(profileDropdown);
	}

	public void clickProfileMenuItem() {
		click(profileMenuItem);
	}

	public void clickLogoutMenuItem() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		for (int i = 0; i < 3; i++) {
			try {
				wait.until(ExpectedConditions.visibilityOf(logoutMenuItem));
				wait.until(ExpectedConditions.elementToBeClickable(logoutMenuItem)).click();
				return;
			} catch (StaleElementReferenceException e) {
				// retry
			}
		}
		throw new RuntimeException("Unable to click Logout menu item");
	}

	// ── Profile Popup ─────────────────────────────────────────

	public boolean isProfilePopupDisplayed() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(profilePopup));
			return true;
		} catch (TimeoutException | StaleElementReferenceException e) {
			return false;
		}
	}

	public boolean isFirstNameFieldDisplayed() {
		return isDisplayed(firstNameField);
	}

	public boolean isLastNameFieldDisplayed() {
		return isDisplayed(lastNameField);
	}

	public boolean isEmailFieldDisplayed() {
		return isDisplayed(emailField);
	}

	public boolean isPrimaryMobileFieldDisplayed() {
		return isDisplayed(primaryMobileField);
	}

	public boolean isSecondaryEmailFieldDisplayed() {
		return isDisplayed(secondaryEmailField);
	}

	public boolean isSecondaryMobileFieldDisplayed() {
		return isDisplayed(secondaryMobileField);
	}

	public boolean isCountryDropdownDisplayed() {
		return isDisplayed(countryDropdown);
	}

	public void updateFirstName(String firstName) {
		type(firstNameField, firstName);
	}

	public void updateLastName(String lastName) {
		type(lastNameField, lastName);
	}

	public void clickUpdate() {
		click(updateButton);
	}

	// ── Success Popup ─────────────────────────────────────────

	public boolean isSuccessPopupDisplayed() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(successPopup));
			return true;
		} catch (TimeoutException | StaleElementReferenceException e) {
			return false;
		}
	}

	public void clickOkOnSuccessPopup() {
		click(successOkButton);
	}

	// ── Full Logout Flow ──────────────────────────────────────

	public void logout() {
		clickProfileIcon();
		clickLogoutMenuItem();
		waitForUrl("login");
	}
}