package pages.superuser;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * SuperUserPage - Actions available only to Super User role.
 */
public class SuperUserPage extends BasePage {

    @FindBy(css = ".super-user-panel")
    private WebElement superUserPanel;

    @FindBy(id = "reportsMenu")
    private WebElement reportsMenu;

    @FindBy(id = "manageAdminsMenu")
    private WebElement manageAdminsMenu;

    @FindBy(css = ".reports-table")
    private WebElement reportsTable;

    public SuperUserPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSuperUserPanelVisible() { return isDisplayed(superUserPanel); }
    public void openReports()                { click(reportsMenu); }
    public void openManageAdmins()           { click(manageAdminsMenu); }
    public boolean isReportsTableVisible()   { return isDisplayed(reportsTable); }
}
