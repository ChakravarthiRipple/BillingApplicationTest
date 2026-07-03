package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model — Change Password Popup
 * Application : billing.ripplemetering.com
 *
 * ✅ Common for ALL 6 User Roles:
 *    SUPER_ADMIN | SUPER_USER |
 *    ADMIN_PREPAID | ADMIN_POSTPAID |
 *    CONSUMER_PREPAID | CONSUMER_POSTPAID
 */
public class ChangePasswordPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ChangePasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  XPATHS
    // ══════════════════════════════════════════════════════════════════════════

    // Modal Header
    private By modalTitle =
        By.xpath("//div[contains(@class,'modal-header')]//h5[text()='Change Password']");

    private By modalCloseBtn =
        By.xpath("//div[contains(@class,'modal-header')]//button[@class='close']");

    // Labels
    private By labelOldPassword =
        By.xpath("//label[contains(text(),'Enter Old Password')]");

    private By labelNewPassword =
        By.xpath("//label[contains(text(),'Enter New Password')]");

    private By labelReEnterPassword =
        By.xpath("//label[contains(text(),'Re-Enter New Password')]");

    // Input Fields
    private By oldPasswordField =
        By.xpath("//input[@placeholder='Old Password']");

    private By newPasswordField =
        By.xpath("//input[@placeholder='New Password']");

    private By reEnterPasswordField =
        By.xpath("//input[@placeholder='Confirm Password']");

    // Eye Icons
    private By oldPasswordEyeIcon =
        By.xpath("//input[@placeholder='Old Password']/following-sibling::span");

    private By newPasswordEyeIcon =
        By.xpath("//input[@placeholder='New Password']/following-sibling::span");

    private By reEnterPasswordEyeIcon =
        By.xpath("//input[@placeholder='Confirm Password']/following-sibling::span");

    // Update Button
    private By updateBtn =
        By.xpath("//button[normalize-space(text())='Update']");

    // Toast Messages
    private By successToast =
        By.xpath("//*[contains(@class,'toast-success')]");

    private By errorToast =
        By.xpath("//*[contains(@class,'toast-error') or contains(@class,'alert-danger')]");

    // ══════════════════════════════════════════════════════════════════════════
    //  MODAL METHODS
    // ══════════════════════════════════════════════════════════════════════════

    /** Verify Change Password popup is displayed */
    public boolean isModalDisplayed() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(modalTitle)
        ).isDisplayed();
    }

    /** Get modal title text */
    public String getModalTitle() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(modalTitle)
        ).getText();
    }

    /** Click X to close the popup */
    public void clickClose() {
        wait.until(ExpectedConditions.elementToBeClickable(modalCloseBtn)).click();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  LABEL METHODS
    // ══════════════════════════════════════════════════════════════════════════

    /** Get "Enter Old Password" label text */
    public String getOldPasswordLabel() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(labelOldPassword)
        ).getText();
    }

    /** Get "Enter New Password" label text */
    public String getNewPasswordLabel() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(labelNewPassword)
        ).getText();
    }

    /** Get "Re-Enter New Password" label text */
    public String getReEnterPasswordLabel() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(labelReEnterPassword)
        ).getText();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  INPUT FIELD METHODS
    // ══════════════════════════════════════════════════════════════════════════

    /** Enter value in Old Password field */
    public void enterOldPassword(String password) {
        WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(oldPasswordField));
        field.clear();
        field.sendKeys(password);
    }

    /** Enter value in New Password field */
    public void enterNewPassword(String password) {
        WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(newPasswordField));
        field.clear();
        field.sendKeys(password);
    }

    /** Enter value in Re-Enter New Password field */
    public void enterReEnterPassword(String password) {
        WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(reEnterPasswordField));
        field.clear();
        field.sendKeys(password);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  EYE ICON METHODS
    // ══════════════════════════════════════════════════════════════════════════

    /** Toggle show/hide on Old Password field */
    public void clickOldPasswordEyeIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(oldPasswordEyeIcon)).click();
    }

    /** Toggle show/hide on New Password field */
    public void clickNewPasswordEyeIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(newPasswordEyeIcon)).click();
    }

    /** Toggle show/hide on Re-Enter New Password field */
    public void clickReEnterPasswordEyeIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(reEnterPasswordEyeIcon)).click();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  SUBMIT METHOD
    // ══════════════════════════════════════════════════════════════════════════

    /** Click the Update button */
    public void clickUpdate() {
        wait.until(ExpectedConditions.elementToBeClickable(updateBtn)).click();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  COMPLETE ACTION METHOD
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Fill all 3 fields and click Update
     *
     * Usage:
     *   profilePopup.clickChangePassword();
     *   changePasswordPage.changePassword("Old@123", "New@456", "New@456");
     *
     * @param oldPassword      → Enter Old Password field
     * @param newPassword      → Enter New Password field
     * @param reEnterPassword  → Re-Enter New Password field
     */
    public void changePassword(String oldPassword, String newPassword, String reEnterPassword) {
        enterOldPassword(oldPassword);
        enterNewPassword(newPassword);
        enterReEnterPassword(reEnterPassword);
        clickUpdate();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  TOAST MESSAGE METHODS
    // ══════════════════════════════════════════════════════════════════════════

    /** Get success toast message text */
    public String getSuccessMessage() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(successToast)
        ).getText();
    }

    /** Get error toast message text */
    public String getErrorMessage() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorToast)
        ).getText();
    }
}