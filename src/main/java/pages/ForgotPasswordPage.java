package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForgotPasswordPage extends UiActions {

    private final By heading       = By.xpath("//*[contains(normalize-space(text()),'Password')]");
    private final By emailLabel    = byText("Email / Mobile Number");
    private final By emailInput    = By.xpath("//input[@placeholder='Email / Mobile Number']");
    private final By sendOtpButton = By.xpath("//button[normalize-space()='Send OTP']");
    private final By backToLogin   = By.xpath("//a[contains(normalize-space(),'Back to Login')]");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("forgotPassword"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public LoginPage clickBackToLogin() {
        click(backToLogin);
        wait.until(ExpectedConditions.urlContains("/auth/login"));
        return new LoginPage(driver);
    }

    public boolean isHeadingDisplayed()       { return isShown(heading); }
    public boolean isEmailLabelDisplayed()    { return isShown(emailLabel); }
    public boolean isEmailFieldDisplayed()    { return isShown(emailInput); }
    public boolean isSendOtpButtonDisplayed() { return isShown(sendOtpButton); }
    public boolean isBackToLoginDisplayed()   { return isShown(backToLogin); }
}