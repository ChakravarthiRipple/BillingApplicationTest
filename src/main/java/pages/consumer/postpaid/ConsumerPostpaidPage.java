package pages.consumer.postpaid;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * ConsumerPostpaidPage - Actions for Consumer Postpaid role.
 */
public class ConsumerPostpaidPage extends BasePage {

    @FindBy(css = ".consumer-postpaid-panel")
    private WebElement consumerPostpaidPanel;

    @FindBy(id = "myBillSection")
    private WebElement billSection;

    @FindBy(id = "payBillBtn")
    private WebElement payBillButton;

    @FindBy(id = "viewUsageBtn")
    private WebElement viewUsageButton;

    @FindBy(css = ".bill-amount")
    private WebElement billAmount;

    @FindBy(css = ".usage-details")
    private WebElement usageDetails;

    public ConsumerPostpaidPage(WebDriver driver) {
        super(driver);
    }

    public boolean isConsumerPostpaidPanelVisible() { return isDisplayed(consumerPostpaidPanel); }
    public boolean isBillSectionVisible()           { return isDisplayed(billSection); }
    public void clickPayBill()                      { click(payBillButton); }
    public void clickViewUsage()                    { click(viewUsageButton); }
    public String  getBillAmount()                  { return getText(billAmount); }
    public boolean isUsageDetailsVisible()          { return isDisplayed(usageDetails); }
}
