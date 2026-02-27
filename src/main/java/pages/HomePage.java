package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * HomePage - Page Object for the Home/Dashboard page.
 * Update @FindBy locators to match your application.
 */
public class HomePage extends BasePage {

    @FindBy(css = ".dashboard-title")
    private WebElement pageTitle;

    @FindBy(css = ".user-profile")
    private WebElement userProfile;

    @FindBy(id = "logoutBtn")
    private WebElement logoutButton;

    @FindBy(css = ".nav-menu")
    private WebElement navigationMenu;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public boolean isUserProfileVisible() {
        return isDisplayed(userProfile);
    }

    public void clickLogout() {
        click(logoutButton);
    }

    public boolean isDashboardLoaded() {
        return isDisplayed(pageTitle);
    }
}
