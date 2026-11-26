package pages;

import constants.UserRole;
import utils.ConfigReader;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ripple login page: /auth/login Covers password login, Login with OTP,
 * validation errors, popups and Forgot password link.
 */
public class LoginPage extends UiActions {

	// ---------------- Locators ----------------
	private final By heading = byContainsText("Welcome Back");
	private final By subHeading = byContainsText("Sign in to continue with Ripple");
	private final By logo = By.xpath("//img");
	private final By emailLabel = byText("Email / Mobile Number");
	private final By emailInput = By.xpath("//input[@placeholder='Enter Email / Mobile']");
	private final By passwordLabel = byText("Password");
	private final By passwordInput = By.xpath("//input[@placeholder='Enter password']");
	private final By passwordEyeIcon = By.xpath("//input[@placeholder='Enter password']/following-sibling::*[1]");
	private final By otpCheckbox = By.xpath("//input[@type='checkbox']");
	private final By otpCheckboxLabel = byText("Login with OTP");
	private final By sendOtpButton = By.xpath("//button[normalize-space()='Send OTP']");
	private final By otpInput = By.xpath("//input[@placeholder='Enter OTP']");
	private final By loginButton = By.xpath("//button[normalize-space()='Log In']");
	private final By forgotLink = By.xpath("//a[contains(normalize-space(),'password?')]");
	private final By dashboardHeading = byText("Welcome To Ripple Dashboard!");

	// Popup (SweetAlert style) with an OK button
	private final By popup = By.xpath("//*[(contains(@class,'swal2-popup') or @role='dialog' or @role='alertdialog')"
			+ "][.//button[normalize-space()='OK']]");
	private final By popupOk = By.xpath("//button[contains(@class,'swal2-confirm') or normalize-space()='OK']");

	// Red banner above the Email field (e.g. "This account is locked out...")
	private final By aboveUsernameError = By.xpath("//div[contains(@class,'alert')]");

	// Inline validation messages under the fields
	private final By emailFieldErrors = By.xpath("//*[contains(normalize-space(text()),'Email / Mobile') "
			+ "and (contains(normalize-space(text()),'required') or contains(normalize-space(text()),'cannot be empty'))]");
	private final By passwordFieldErrors = By.xpath("//*[normalize-space(text())='Password is required']");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// ---------------- Login actions ----------------

	public void login(String userName, String password) {
		type(emailInput, userName);
		type(passwordInput, password);
		click(loginButton);
	}

	/** TODO: if your original loginAs(UserRole) body is different, keep yours. */
	public void loginAs(UserRole role) {
		login(ConfigReader.getUsername(role), ConfigReader.getPassword(role));
	}

	public void loginWithInvalidPassword(String userName, String wrongPassword) {
		login(userName, wrongPassword);
	}

	public void clickLoginWithoutCredentials() {
		click(loginButton);
	}

	public void enterPassword(String password) {
		type(passwordInput, password);
	}

	public void clickPasswordEyeIcon() {
		click(passwordEyeIcon);
	}

	// ---------------- Login with OTP ----------------

	/** Ticks only the checkbox. Does NOT send an OTP. */
	public void enableLoginWithOtp() {
		if (!isOtpCheckboxChecked()) {
			click(otpCheckbox);
		}
	}

	/**
	 * Enters the email, ticks Login with OTP and clicks Send OTP (this sends a real
	 * OTP).
	 */
	public void selectLoginWithOtp(String userName) {
		type(emailInput, userName);
		enableLoginWithOtp();
		click(sendOtpButton);
		dismissPopupIfPresent(); // "OTP has been sent successfully."
	}

	public void enterOtpAndLogin(String otp) {
		type(otpInput, otp);
		click(loginButton);
	}

	// ---------------- Popups ----------------

	public boolean isErrorPopupDisplayed() {
		return isShown(popup);
	}

	public void clickOkOnErrorPopup() {
		click(popupOk);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(popup));
	}

	public boolean isInvalidOtpPopupDisplayed() {
		return isShown(popup) && popupText().toLowerCase().contains("otp");
	}

	public void clickInvalidOtpPopupOk() {
		clickOkOnErrorPopup();
	}

	public String getPopupText() {
		return popupText();
	}

	private String popupText() {
		return visible(popup).getText().trim();
	}

	private void dismissPopupIfPresent() {
		if (isShown(popup)) {
			click(popupOk);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(popup));
		}
	}

	// ---------------- Error messages ----------------

	public boolean isAboveUsernameErrorDisplayed() {
		return isShown(aboveUsernameError);
	}

	public String getAboveUsernameErrorText() {
		return visible(aboveUsernameError).getText().trim();
	}

	public boolean isInvalidOtpMessageDisplayedOnUsername() {
		return isShown(aboveUsernameError) && getAboveUsernameErrorText().toLowerCase().contains("otp");
	}

	public boolean isEmailFieldErrorDisplayed() {
		return isShown(emailFieldErrors);
	}

	public boolean isPasswordFieldErrorDisplayed() {
		return isShown(passwordFieldErrors);
	}

	public String getEmailFieldErrorText() {
		return joinedText(emailFieldErrors);
	}

	public String getPasswordFieldErrorText() {
		return joinedText(passwordFieldErrors);
	}

	private String joinedText(By locator) {
		visible(locator);
		List<WebElement> els = driver.findElements(locator);
		return els.stream().filter(WebElement::isDisplayed).map(e -> e.getText().trim())
				.collect(Collectors.joining(" | "));
	}

	// ---------------- Navigation ----------------

	public ForgotPasswordPage clickForgotPassword() {
		click(forgotLink);
		return new ForgotPasswordPage(driver);
	}

	// ---------------- State checks ----------------

	public boolean isDashboardLoaded(UserRole role) {
	    try {
	        wait.until(ExpectedConditions.urlContains(ConfigReader.getDashboardPath(role)));
	        return true;
	    } catch (TimeoutException e) {
	        return false;
	    }
	}

	public boolean isOnLoginPage() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        return wait.until(
	            ExpectedConditions.urlContains("/auth/login")
	        );

	    } catch (TimeoutException e) {
	        System.out.println("Expected login URL not found.");
	        System.out.println("Actual URL: " + driver.getCurrentUrl());
	        return false;
	    }
	}

	/** True while the password text is masked (input type="password"). */
	public boolean isPasswordMasked() {
		return "password".equalsIgnoreCase(visible(passwordInput).getAttribute("type"));
	}

	public boolean isOtpCheckboxChecked() {
		return visible(otpCheckbox).isSelected();
	}

	/** In OTP mode the password field is replaced by the OTP field. */
	public boolean isPasswordFieldHidden() {
		for (WebElement el : driver.findElements(passwordInput)) {
			if (el.isDisplayed()) {
				return false;
			}
		}
		return true;
	}

	// ---------------- UI element checks ----------------

	public boolean isHeadingDisplayed() {
		return isShown(heading);
	}

	public boolean isSubHeadingDisplayed() {
		return isShown(subHeading);
	}

	public boolean isLogoDisplayed() {
		return isShown(logo);
	}

	public boolean isEmailLabelDisplayed() {
		return isShown(emailLabel);
	}

	public boolean isEmailFieldDisplayed() {
		return isShown(emailInput);
	}

	public boolean isPasswordLabelDisplayed() {
		return isShown(passwordLabel);
	}

	public boolean isPasswordFieldDisplayed() {
		return isShown(passwordInput);
	}

	public boolean isPasswordEyeIconDisplayed() {
		return isShown(passwordEyeIcon);
	}

	public boolean isOtpCheckboxDisplayed() {
		return isShown(otpCheckbox) && isShown(otpCheckboxLabel);
	}

	public boolean isSendOtpButtonDisplayed() {
		return isShown(sendOtpButton);
	}

	public boolean isOtpFieldDisplayed() {
		return isShown(otpInput);
	}

	public boolean isLoginButtonDisplayed() {
		return isShown(loginButton);
	}

	public boolean isLoginButtonEnabled() {
		return visible(loginButton).isEnabled();
	}

	public boolean isForgotPasswordLinkDisplayed() {
		return isShown(forgotLink);
	}
}