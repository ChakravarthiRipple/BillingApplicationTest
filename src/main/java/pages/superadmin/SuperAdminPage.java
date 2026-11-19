package pages.superadmin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.UiActions;

public class SuperAdminPage extends UiActions {

    public static final String[] SIDEBAR_MENUS = {
            "Dashboard", "Masters & Suppliers", "Projects", "Users", "Payments",
            "Statement Ads", "Meters", "Meters Database", "Smart Leak Devices",
            "EV Readings", "Reports", "FAQ's", "Feedback History", "AI Assistant"
    };

    private final By welcomeHeading   = byText("Welcome To Ripple Dashboard!");
    private final By userTable        = By.xpath("//table");   // tighten to the Users list table
    private final By notificationBell = By.xpath("//*[contains(@class,'bell')]");
    private final By hamburgerToggle  = By.xpath("//*[contains(@class,'bars') or contains(@class,'hamburger') or contains(@class,'toggle')]");

    public SuperAdminPage(WebDriver driver) {
        super(driver);
    }

    private By menu(String name) {
        return By.xpath("//a[normalize-space(.)=\"" + name + "\"]");
    }

    public void openMenu(String name) {
        click(menu(name));
        if (name.equals("Dashboard")) {
            wait.until(ExpectedConditions.urlContains("/dashboard"));
        } else {
            wait.until(d -> !d.getCurrentUrl().endsWith("/dashboard"));
        }
    }

    public void openManageUsers() {
        openMenu("Users");
    }

    public boolean isUserListDisplayed() {
        return isShown(userTable);
    }

    public boolean isSuperAdminPanelVisible() {
        return isShown(menu("Users")) && isShown(menu("Masters & Suppliers"));
    }

    public boolean isMenuVisible(String name)    { return isShown(menu(name)); }
    public boolean isWelcomeHeadingDisplayed()   { return isShown(welcomeHeading); }
    public boolean isNotificationBellDisplayed() { return isShown(notificationBell); }
    public boolean isHamburgerDisplayed()        { return isShown(hamburgerToggle); }

    public boolean isSectionDisplayed(String title) {
        return isShown(byText(title));
    }

    public boolean isCardDisplayed(String label) {
        return isShown(byText(label));
    }

    private By cardValue(String label) {
        return By.xpath("(//*[normalize-space(text())=\"" + label + "\"]/following::*[normalize-space(text())!=''])[1]");
    }

    public String getCardText(String label) {
        return visible(cardValue(label)).getText().trim();
    }

    public int getCardCount(String label) {
        return Integer.parseInt(getCardText(label).replaceAll("[^0-9]", ""));
    }
}