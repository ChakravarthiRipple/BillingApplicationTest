package pages.admin.postpaid;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * AdminPostpaidPage - Actions for Admin Postpaid role.
 */
public class AdminPostpaidPage extends BasePage {

    @FindBy(css = ".admin-postpaid-panel")
    private WebElement adminPostpaidPanel;

    @FindBy(id = "postpaidPlansMenu")
    private WebElement postpaidPlansMenu;

    @FindBy(id = "billingMenu")
    private WebElement billingMenu;

    @FindBy(id = "invoicesMenu")
    private WebElement invoicesMenu;

    @FindBy(css = ".billing-table")
    private WebElement billingTable;

    public AdminPostpaidPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAdminPostpaidPanelVisible() { return isDisplayed(adminPostpaidPanel); }
    public void openPostpaidPlans()              { click(postpaidPlansMenu); }
    public void openBilling()                    { click(billingMenu); }
    public void openInvoices()                   { click(invoicesMenu); }
    public boolean isBillingTableVisible()       { return isDisplayed(billingTable); }
}
