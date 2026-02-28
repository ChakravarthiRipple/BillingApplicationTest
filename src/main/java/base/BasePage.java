package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage - Reusable Selenium actions for all Page Objects.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement loginButton) {
        log.info("Click: {}", loginButton);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void type(WebElement el, String text) {
        log.info("Type '{}' into: {}", text, el);
        wait.until(ExpectedConditions.visibilityOf(el)).clear();
        el.sendKeys(text);
    }

    public String getText(WebElement el) {
        wait.until(ExpectedConditions.visibilityOf(el));
        return el.getText();
    }

    public boolean isDisplayed(WebElement el) {
        try { return el.isDisplayed(); } catch (NoSuchElementException e) { return false; }
    }

    public void waitForUrl(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    public void selectByText(WebElement el, String text) {
        new Select(el).selectByVisibleText(text);
    }

    public void scrollTo(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
    }

    public byte[] screenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public String getPageTitle() { return driver.getTitle(); }
    public String getCurrentUrl() { return driver.getCurrentUrl(); }
}
