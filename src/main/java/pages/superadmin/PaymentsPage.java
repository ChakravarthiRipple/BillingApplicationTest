package pages.superadmin;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentsPage extends BasePage {

    @FindBy(xpath = "//*[contains(text(),'Payments')]")
    private WebElement pageHeader;

    @FindBy(xpath = "//table | //*[contains(@class,'table') or contains(@class,'grid')]")
    private WebElement paymentsTable;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
    private WebElement searchField;

    @FindBy(xpath = "//input[@type='date'] | //*[contains(@class,'date-picker')]")
    private WebElement datePicker;

    public PaymentsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded()        { return isDisplayed(pageHeader); }
    public String getPageHeader()    { return getText(pageHeader); }
    public boolean isTableVisible()  { return isDisplayed(paymentsTable); }
    public void search(String text)  { type(searchField, text); }
}