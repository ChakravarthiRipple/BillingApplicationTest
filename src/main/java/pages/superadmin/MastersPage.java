package pages.superadmin;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MastersPage extends BasePage {

    @FindBy(xpath = "//*[contains(text(),'Masters') or contains(text(),'Suppliers')]")
    private WebElement pageHeader;

    @FindBy(xpath = "//button[contains(text(),'Add')] | //a[contains(text(),'Add')]")
    private WebElement addButton;

    @FindBy(xpath = "//table | //*[contains(@class,'table') or contains(@class,'grid')]")
    private WebElement dataTable;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
    private WebElement searchField;

    public MastersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded()          { return isDisplayed(pageHeader); }
    public String getPageHeader()      { return getText(pageHeader); }
    public boolean isTableVisible()    { return isDisplayed(dataTable); }
    public void clickAdd()             { click(addButton); }
    public void search(String text)    { type(searchField, text); }
}