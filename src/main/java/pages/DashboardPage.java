package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * DashboardPage - Common dashboard/home elements shared across roles.
 * Extend this for role-specific dashboard pages if needed.
 */
public class DashboardPage extends BasePage {

    @FindBy(css = ".dashboard-container")
    private WebElement dashboardContainer;

    @FindBy(css = ".user-role-badge")
    private WebElement roleBadge;

    @FindBy(css = ".user-name-display")
    private WebElement userNameDisplay;

    @FindBy(id = "logoutBtn")
    private WebElement logoutButton;

    @FindBy(css = ".nav-sidebar")
    private WebElement sidebarNav;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardLoaded()    { return isDisplayed(dashboardContainer); }
    public String  getRoleBadgeText()     { return getText(roleBadge); }
    public String  getLoggedInUserName()  { return getText(userNameDisplay); }
    public boolean isSidebarVisible()     { return isDisplayed(sidebarNav); }

    public void logout() {
        click(logoutButton);
        waitForUrl("login");
    }
}
