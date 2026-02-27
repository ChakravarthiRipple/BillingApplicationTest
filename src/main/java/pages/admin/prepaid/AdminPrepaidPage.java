package pages.admin.prepaid;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * AdminPrepaidPage - Actions for Admin Prepaid role.
 */
public class AdminPrepaidPage extends BasePage {

    @FindBy(css = ".admin-prepaid-panel")
    private WebElement adminPrepaidPanel;

    @FindBy(id = "prepaidPlansMenu")
    private WebElement prepaidPlansMenu;

    @FindBy(id = "manageConsumersMenu")
    private WebElement manageConsumersMenu;

    @FindBy(id = "rechargeHistoryMenu")
    private WebElement rechargeHistoryMenu;

    @FindBy(css = ".prepaid-plans-table")
    private WebElement plansTable;

    public AdminPrepaidPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAdminPrepaidPanelVisible() { return isDisplayed(adminPrepaidPanel); }
    public void openPrepaidPlans()              { click(prepaidPlansMenu); }
    public void openManageConsumers()           { click(manageConsumersMenu); }
    public void openRechargeHistory()           { click(rechargeHistoryMenu); }
    public boolean isPlansTableVisible()        { return isDisplayed(plansTable); }
}
