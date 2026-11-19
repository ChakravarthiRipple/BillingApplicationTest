package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChangePasswordPage extends UiActions {

    private final By modal           = By.xpath("//div[contains(@class,'modal-content')]");
    private final By title           = By.xpath("//div[contains(@class,'modal-header')]//*[normalize-space()='Change Password']");
    private final By close           = By.xpath("//div[contains(@class,'modal-header')]//button");
    private final By oldPassword     = By.xpath("//input[@placeholder='Old Password']");
    private final By newPassword     = By.xpath("//input[@placeholder='New Password']");
    private final By confirmPassword = By.xpath("//input[@placeholder='Confirm Password']");
    private final By updateButton    = By.xpath("//div[contains(@class,'modal-content')]//button[normalize-space()='Update']");

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed()                     { return isShown(title); }
    public boolean isOldPasswordLabelDisplayed()     { return isShown(byContainsText("Enter Old Password")); }
    public boolean isNewPasswordLabelDisplayed()     { return isShown(byContainsText("Enter New Password")); }
    public boolean isConfirmPasswordLabelDisplayed() { return isShown(byContainsText("Re-Enter New Password")); }
    public boolean isOldPasswordFieldDisplayed()     { return isShown(oldPassword); }
    public boolean isNewPasswordFieldDisplayed()     { return isShown(newPassword); }
    public boolean isConfirmPasswordFieldDisplayed() { return isShown(confirmPassword); }
    public boolean isUpdateButtonDisplayed()         { return isShown(updateButton); }

    public void closePopup() {
        click(close);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));
    }
}