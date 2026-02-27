package pages;

import base.BasePage;
import constants.UserRole;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ConfigReader;

/**
 * LoginPage - Shared login page used by ALL 6 roles.
 * Update @FindBy locators to match your application.
 */
public class LoginPage extends BasePage {

    @FindBy(id = "email")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@class='form-control ng-untouched ng-pristine ng-invalid']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@class='btn btn-primary']")
    private WebElement loginButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    @FindBy(css = ".role-indicator")   // optional: shows role after login
    private WebElement roleLabel;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) { type(usernameField, username); }
    public void enterPassword(String password) { type(passwordField, password); }
    public void clickLogin()                   { click(loginButton); }

    /** Login using explicit credentials */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    /** Login using a UserRole - credentials loaded from config.properties */
    public void loginAs(UserRole role) {
        login(ConfigReader.getUsername(role), ConfigReader.getPassword(role));
    }

    public boolean isErrorDisplayed()  { return isDisplayed(errorMessage); }
    public String  getErrorMessage()   { return getText(errorMessage); }
    public String  getRoleLabel()      { return getText(roleLabel); }
}
