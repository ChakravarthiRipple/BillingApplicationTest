package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * HomePage - Dashboard page for all 6 roles.
 * All roles see "Welcome To Ripple Dashboard!" after login.
 */
public class HomePage extends BasePage {

    // ── Dashboard Welcome Message ─────────────────────────────
    @FindBy(xpath = "//h1[contains(text(),'Welcome To Ripple Dashboard!')] | //h2[contains(text(),'Welcome To Ripple Dashboard!')] | //*[contains(text(),'Welcome To Ripple Dashboard!')]")
    private WebElement welcomeMessage;

    // ── Profile Icon (top right corner) ──────────────────────
    @FindBy(xpath = "//div[contains(@class,'profile') or contains(@class,'avatar') or contains(@class,'user-icon')] | //img[contains(@class,'profile') or contains(@class,'avatar')]")
    private WebElement profileIcon;

    // ── Profile Dropdown ──────────────────────────────────────
    @FindBy(xpath = "//ul[contains(@class,'dropdown')] | //div[contains(@class,'dropdown-menu')]")
    private WebElement profileDropdown;

    @FindBy(xpath = "//a[contains(text(),'Profile')] | //span[contains(text(),'Profile')] | //li[contains(text(),'Profile')]")
    private WebElement profileMenuItem;

    @FindBy(xpath = "//a[contains(text(),'Logout')] | //span[contains(text(),'Logout')] | //li[contains(text(),'Logout')] | //button[contains(text(),'Logout')]")
    private WebElement logoutMenuItem;

    // ── Profile Popup Fields ──────────────────────────────────
    @FindBy(xpath = "//div[contains(@class,'modal') or contains(@class,'popup') or contains(@class,'profile-form')]")
    private WebElement profilePopup;

    @FindBy(xpath = "//input[@formcontrolname='firstName'] | //input[contains(@placeholder,'First Name')] | //input[@id='firstName']")
    private WebElement firstNameField;

    @FindBy(xpath = "//input[@formcontrolname='lastName'] | //input[contains(@placeholder,'Last Name')] | //input[@id='lastName']")
    private WebElement lastNameField;

    @FindBy(xpath = "//input[@formcontrolname='email'] | //input[contains(@placeholder,'Email')] | //input[@id='email'] | //input[@type='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@formcontrolname='primaryMobile'] | //input[contains(@placeholder,'Primary')] | //input[@id='primaryMobile']")
    private WebElement primaryMobileField;

    @FindBy(xpath = "//input[@formcontrolname='secondaryEmail'] | //input[contains(@placeholder,'Secondary Email')] | //input[@id='secondaryEmail']")
    private WebElement secondaryEmailField;

    @FindBy(xpath = "//input[@formcontrolname='secondaryMobile'] | //input[contains(@placeholder,'Secondary')] | //input[@id='secondaryMobile']")
    private WebElement secondaryMobileField;

    @FindBy(xpath = "//select[@formcontrolname='country'] | //select[contains(@id,'country')] | //ng-select[contains(@id,'country')]")
    private WebElement countryDropdown;

    @FindBy(xpath = "//*[contains(text(),'Verify email address') or contains(text(),'verify email') or contains(text(),'mobile login')]")
    private WebElement verifyEmailOrMobileLink;

    // ── Update Button ─────────────────────────────────────────
    @FindBy(xpath = "//button[contains(text(),'Update')] | //button[contains(text(),'Save')] | //button[contains(text(),'Submit')]")
    private WebElement updateButton;

    // ── Update Success Popup ──────────────────────────────────
    @FindBy(xpath = "//div[contains(@class,'success') or contains(@class,'swal') or contains(@class,'alert-success')] | //*[contains(text(),'successful') or contains(text(),'Successfully') or contains(text(),'Updated')]")
    private WebElement successPopup;

    @FindBy(xpath = "//button[text()='OK' or text()='Ok' or text()='Okay'] | //button[contains(@class,'confirm')] | //button[contains(@class,'swal2-confirm')]")
    private WebElement successOkButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ── Dashboard ─────────────────────────────────────────────

    public boolean isDashboardLoaded() {
        return isDisplayed(welcomeMessage);
    }

    public String getWelcomeMessage() {
        return getText(welcomeMessage);
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
        click(logoutMenuItem);
    }

    // ── Profile Popup ─────────────────────────────────────────

    public boolean isProfilePopupDisplayed() {
        return isDisplayed(profilePopup);
    }

    public boolean isFirstNameFieldDisplayed()      { return isDisplayed(firstNameField); }
    public boolean isLastNameFieldDisplayed()       { return isDisplayed(lastNameField); }
    public boolean isEmailFieldDisplayed()          { return isDisplayed(emailField); }
    public boolean isPrimaryMobileFieldDisplayed()  { return isDisplayed(primaryMobileField); }
    public boolean isSecondaryEmailFieldDisplayed() { return isDisplayed(secondaryEmailField); }
    public boolean isSecondaryMobileFieldDisplayed(){ return isDisplayed(secondaryMobileField); }
    public boolean isCountryDropdownDisplayed()     { return isDisplayed(countryDropdown); }
    public boolean isVerifyEmailOrMobileLinkDisplayed() { return isDisplayed(verifyEmailOrMobileLink); }

    public void updateFirstName(String firstName) { type(firstNameField, firstName); }
    public void updateLastName(String lastName)   { type(lastNameField, lastName); }

    public void clickUpdate() {
        click(updateButton);
    }

    // ── Success Popup ─────────────────────────────────────────

    public boolean isSuccessPopupDisplayed() {
        return isDisplayed(successPopup);
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