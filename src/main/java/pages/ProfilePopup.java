package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePopup extends UiActions {

    // Header dropdown (replace the trigger locator with a stable id if you have one)
    private final By userMenuTrigger = By.xpath("(//a[contains(@class,'dropdown-toggle')])[last()]");
    private final By profileOption   = By.xpath("//a[normalize-space()='Profile']");
    private final By changePwdOption = By.xpath("//a[normalize-space()='Change password']");
    private final By logoutOption    = By.xpath("//a[normalize-space()='Logout']");

    // Profile modal
    private final By modal           = By.xpath("//div[contains(@class,'modal-content')]");
    private final By modalTitle      = By.xpath("//div[contains(@class,'modal-header')]//*[normalize-space()='Profile']");
    private final By modalClose      = By.xpath("//div[contains(@class,'modal-header')]//button");
    private final By firstNameInput  = By.xpath("//label[contains(normalize-space(),'First Name')]/following::input[1]");
    private final By lastNameInput   = By.xpath("//label[contains(normalize-space(),'Last Name')]/following::input[1]");
    private final By emailInput      = By.xpath("//label[contains(normalize-space(),'Email Address')]/following::input[1]");
    private final By secondaryEmail  = By.xpath("//input[@placeholder='Enter Secondary Email']");
    private final By secondaryPhone  = By.xpath("//input[@placeholder='Enter Phone Number']");
    private final By changePicture   = By.xpath("//a[normalize-space()='Change picture']");
    private final By updateButton    = By.xpath("//div[contains(@class,'modal-content')]//button[normalize-space()='Update']");

    // Success alert
    private final By alertOkButton     = By.xpath("//button[normalize-space()='OK']");
    private final By profileUpdatedMsg = byText("User profile updated successfully.");

    public ProfilePopup(WebDriver driver) {
        super(driver);
    }

    public void open() {
        click(userMenuTrigger);
        visible(logoutOption);
    }

    public void logout() {
        open();
        click(logoutOption);
        wait.until(ExpectedConditions.urlContains("login"));
    }

    public void openProfile() {
        open();
        click(profileOption);
        visible(modalTitle);
    }
    public ChangePasswordPage openChangePassword() {
        open();
        click(changePwdOption);
        return new ChangePasswordPage(driver);
    }


    public String getDisplayedUserName() {
        return visible(userMenuTrigger).getText().trim();
    }

    public boolean isProfileOptionDisplayed()        { return isShown(profileOption); }
    public boolean isChangePasswordOptionDisplayed() { return isShown(changePwdOption); }
    public boolean isLogoutOptionDisplayed()         { return isShown(logoutOption); }

    public boolean isProfileModalDisplayed()         { return isShown(modalTitle); }
    public String  getFirstName()                    { return valueOf(firstNameInput); }
    public String  getLastName()                     { return valueOf(lastNameInput); }
    public String  getEmail()                        { return valueOf(emailInput); }

    public boolean isFirstNameLabelDisplayed()       { return isShown(byContainsText("First Name")); }
    public boolean isLastNameLabelDisplayed()        { return isShown(byContainsText("Last Name")); }
    public boolean isEmailLabelDisplayed()           { return isShown(byContainsText("Email Address")); }
    public boolean isPrimaryMobileLabelDisplayed()   { return isShown(byContainsText("Mobile Number - Primary")); }
    public boolean isSecondaryEmailDisplayed()       { return isShown(secondaryEmail); }
    public boolean isSecondaryMobileLabelDisplayed() { return isShown(byContainsText("Mobile Number - Secondary")); }
    public boolean isSecondaryPhoneDisplayed()       { return isShown(secondaryPhone); }
    public boolean isChangePictureLinkDisplayed()    { return isShown(changePicture); }
    public boolean isUpdateButtonDisplayed()         { return isShown(updateButton); }

    /** Clicks Update with the existing values and returns true if the success alert appears. */
    public boolean updateProfileWithoutChanges() {
        click(updateButton);
        boolean success = isShown(profileUpdatedMsg);
        if (success) {
            click(alertOkButton);
        }
        return success;
    }

    public void closeProfileModal() {
        click(modalClose);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));
    }
}