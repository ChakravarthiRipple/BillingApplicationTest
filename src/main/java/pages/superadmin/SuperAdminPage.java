package pages.superadmin;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * SuperAdminPage - Actions available only to Super Admin role.
 * Update locators to match your application.
 */
public class SuperAdminPage extends BasePage {

    @FindBy(css = ".super-admin-panel")
    private WebElement adminPanel;

    @FindBy(id = "manageUsersMenu")
    private WebElement manageUsersMenu;

    @FindBy(id = "systemSettingsMenu")
    private WebElement systemSettingsMenu;

    @FindBy(id = "auditLogsMenu")
    private WebElement auditLogsMenu;

    @FindBy(id = "createUserBtn")
    private WebElement createUserButton;

    @FindBy(css = ".user-list-table")
    private WebElement userListTable;

    public SuperAdminPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSuperAdminPanelVisible() { return isDisplayed(adminPanel); }
    public void openManageUsers()             { click(manageUsersMenu); }
    public void openSystemSettings()          { click(systemSettingsMenu); }
    public void openAuditLogs()               { click(auditLogsMenu); }
    public void clickCreateUser()             { click(createUserButton); }
    public boolean isUserListDisplayed()      { return isDisplayed(userListTable); }
}
