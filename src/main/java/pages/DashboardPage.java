package pages;

import base.BasePage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * DashboardPage - Common dashboard/home elements shared across roles. Extend
 * this for role-specific dashboard pages if needed.
 */
public class DashboardPage extends BasePage {

	@FindBy(xpath = "//h4[contains(text(),'Welcome To Ripple Dashboard!')]")
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

	public boolean isDashboardLoaded() {
		try {
			wait.until(ExpectedConditions.visibilityOf(dashboardContainer));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getRoleBadgeText() {
		return getText(roleBadge);
	}

	public String getLoggedInUserName() {
		return getText(userNameDisplay);
	}

	public boolean isSidebarVisible() {
		return isDisplayed(sidebarNav);
	}

	public void logout() {
		click(logoutButton);
		waitForUrl("login");
	}
}
