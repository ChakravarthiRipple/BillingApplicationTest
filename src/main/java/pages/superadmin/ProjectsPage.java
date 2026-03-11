package pages.superadmin;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectsPage extends BasePage {

    @FindBy(xpath = "//*[contains(text(),'Projects')]")
    private WebElement pageHeader;

    @FindBy(xpath = "//button[contains(text(),'Add')] | //a[contains(text(),'Add Project')]")
    private WebElement addProjectButton;

    @FindBy(xpath = "//table | //*[contains(@class,'table') or contains(@class,'grid')]")
    private WebElement projectsTable;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
    private WebElement searchField;

    @FindBy(xpath = "//*[contains(@class,'pagination') or contains(text(),'Next')]")
    private WebElement pagination;

    public ProjectsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded()         { return isDisplayed(pageHeader); }
    public String getPageHeader()     { return getText(pageHeader); }
    public boolean isTableVisible()   { return isDisplayed(projectsTable); }
    public void clickAddProject()     { click(addProjectButton); }
    public void search(String text)   { type(searchField, text); }
    public boolean isPaginationVisible(){ return isDisplayed(pagination); }
}