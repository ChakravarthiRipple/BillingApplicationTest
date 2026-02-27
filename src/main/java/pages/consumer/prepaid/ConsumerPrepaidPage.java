package pages.consumer.prepaid;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * ConsumerPrepaidPage - Actions for Consumer Prepaid role.
 */
public class ConsumerPrepaidPage extends BasePage {

    @FindBy(css = ".consumer-prepaid-panel")
    private WebElement consumerPrepaidPanel;

    @FindBy(id = "myBalanceSection")
    private WebElement balanceSection;

    @FindBy(id = "rechargeBtn")
    private WebElement rechargeButton;

    @FindBy(id = "viewPlansBtn")
    private WebElement viewPlansButton;

    @FindBy(css = ".transaction-history")
    private WebElement transactionHistory;

    @FindBy(css = ".balance-amount")
    private WebElement balanceAmount;

    public ConsumerPrepaidPage(WebDriver driver) {
        super(driver);
    }

    public boolean isConsumerPrepaidPanelVisible() { return isDisplayed(consumerPrepaidPanel); }
    public boolean isBalanceSectionVisible()       { return isDisplayed(balanceSection); }
    public void clickRecharge()                    { click(rechargeButton); }
    public void clickViewPlans()                   { click(viewPlansButton); }
    public boolean isTransactionHistoryVisible()   { return isDisplayed(transactionHistory); }
    public String  getBalanceAmount()              { return getText(balanceAmount); }
}
