package pages;

import base.BasePage;
import constants.UserRole;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;

/**
 * LoginPage - All login scenarios for your application.
 */
public class LoginPage extends BasePage {

	// ── Fields ────────────────────────────────────────────────
	@FindBy(id = "email")
	private WebElement emailField;

	@FindBy(xpath = "//input[@type='password']")
	private WebElement passwordField;

	@FindBy(xpath = "//button[@class='btn btn-primary']")
	private WebElement loginButton;

	// ── Error elements ────────────────────────────────────────
	@FindBy(xpath = "//div[contains(text(),'Invalid Email or Password. Attempts remaining: 4')]")
	private WebElement errorPopup;

	@FindBy(xpath = "//button[@class='swal2-confirm swal2-styled']")
	private WebElement errorPopupOkButton;

	// Error shown ABOVE username field after invalid login
	@FindBy(xpath = "//div[@class='alert alert-danger text-center mb-4 p-2 mt-3']")
	private WebElement aboveUsernameError;

	// Inline error under email field
	@FindBy(xpath = "//input[@id='email']/following-sibling::*[contains(@class,'error') or contains(@class,'invalid')]")
	private WebElement emailFieldError;

	// Inline error under password field
	@FindBy(xpath = "//div[@class='invalid-feedback']")
	private WebElement passwordFieldError;

	// ── OTP elements ──────────────────────────────────────────
	@FindBy(id = "remember-check")
	private WebElement loginWithOtpCheckbox;

	@FindBy(id = "password-addon")
	private WebElement sendOtpButton;

	@FindBy(xpath = "//input[@class='form-control ng-untouched ng-pristine ng-invalid']")
	private WebElement otpInputField;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 1: Valid Login — email + password
	// ──────────────────────────────────────────────────────────
	public void login(String email, String password) {
		type(emailField, email);
		type(passwordField, password);
		click(loginButton);
	}

	// Login using role from config.properties
	public void loginAs(UserRole role) {
		// ONLY calls login() - nothing else
		login(ConfigReader.getUsername(role), ConfigReader.getPassword(role));
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 2: Invalid Login — triggers error popup
	// ──────────────────────────────────────────────────────────
	public void loginWithInvalidPassword(String email, String wrongPassword) {
		type(emailField, email);
		type(passwordField, wrongPassword);
		click(loginButton);
	}

	// Click OK on the error popup
	public void clickOkOnErrorPopup() {
		click(errorPopupOkButton);
	}

	// Check if error popup is displayed
	public boolean isErrorPopupDisplayed() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.visibilityOf(errorPopup));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	// Check error shown above username after failed login
	public boolean isAboveUsernameErrorDisplayed() {
		return isDisplayed(aboveUsernameError);
	}

	public String getAboveUsernameErrorText() {
		return getText(aboveUsernameError);
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 3: Direct click login — no email/password entered
	// ──────────────────────────────────────────────────────────
	public void clickLoginWithoutCredentials() {
		click(loginButton);
	}

	public boolean isEmailFieldErrorDisplayed() {
		return isDisplayed(emailFieldError);
	}

	public boolean isPasswordFieldErrorDisplayed() {
		return isDisplayed(passwordFieldError);
	}

	public String getEmailFieldErrorText() {
		return getText(emailFieldError);
	}

	public String getPasswordFieldErrorText() {
		return getText(passwordFieldError);
	}

	// ──────────────────────────────────────────────────────────
	// SCENARIO 4: Login with OTP
	// ──────────────────────────────────────────────────────────
	public void selectLoginWithOtp(String email) {
		type(emailField, email);
		click(loginWithOtpCheckbox);
		click(sendOtpButton);
	}

	public void enterOtpAndLogin(String otp) {
		type(otpInputField, otp);
		click(loginButton);
	}

	public boolean isSendOtpButtonDisplayed() {
		return isDisplayed(sendOtpButton);
	}

	public boolean isOtpFieldDisplayed() {
		return isDisplayed(otpInputField);
	}
}