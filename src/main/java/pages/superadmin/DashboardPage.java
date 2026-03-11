package pages.superadmin;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(xpath = "//*[contains(text(),'Welcome To Ripple Dashboard!')]")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//*[contains(text(),'Wallet Balance')]")
    private WebElement walletBalanceCard;

    @FindBy(xpath = "//*[contains(text(),'Total Projects')]")
    private WebElement totalProjectsCard;

    @FindBy(xpath = "//*[contains(text(),'Active Projects')]")
    private WebElement activeProjectsCard;

    @FindBy(xpath = "//*[contains(text(),'Inactive Projects')]")
    private WebElement inactiveProjectsCard;

    @FindBy(xpath = "//*[contains(text(),'Communication Status')]")
    private WebElement communicationStatusSection;

    @FindBy(xpath = "//*[contains(text(),'Residential Units')]")
    private WebElement residentialUnitsSection;

    @FindBy(xpath = "//*[contains(text(),'Occupied Consumers')]")
    private WebElement occupiedConsumersSection;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded()                        { return isDisplayed(welcomeMessage); }
    public String getWelcomeMessage()                { return getText(welcomeMessage); }
    public boolean isWalletBalanceVisible()          { return isDisplayed(walletBalanceCard); }
    public boolean isTotalProjectsVisible()          { return isDisplayed(totalProjectsCard); }
    public boolean isActiveProjectsVisible()         { return isDisplayed(activeProjectsCard); }
    public boolean isInactiveProjectsVisible()       { return isDisplayed(inactiveProjectsCard); }
    public boolean isCommunicationStatusVisible()    { return isDisplayed(communicationStatusSection); }
    public boolean isResidentialUnitsVisible()       { return isDisplayed(residentialUnitsSection); }
    public boolean isOccupiedConsumersVisible()      { return isDisplayed(occupiedConsumersSection); }
}