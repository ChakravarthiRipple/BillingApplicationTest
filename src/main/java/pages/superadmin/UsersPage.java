package pages.superadmin;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersPage extends BasePage {

    @FindBy(xpath = "//*[contains(text(),'Users')]")
    private WebElement pageHeader;

    @FindBy(xpath = "//button[contains(text(),'Add')] | //a[contains(text(),'Add User')]")
    private WebElement addUserButton;

    @FindBy(xpath = "//table | //*[contains(@class,'table') or contains(@class,'grid')]")
    private WebElement usersTable;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
    private WebElement searchField;

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded()       { return isDisplayed(pageHeader); }
    public String getPageHeader()   { return getText(pageHeader); }
    public boolean isTableVisible() { return isDisplayed(usersTable); }
    public void clickAddUser()      { click(addUserButton); }
    public void search(String text) { type(searchField, text); }
}