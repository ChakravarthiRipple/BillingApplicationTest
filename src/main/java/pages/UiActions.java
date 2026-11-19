package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class UiActions {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected UiActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String value) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(value);
    }

    protected boolean isShown(By locator) {
        try {
            return visible(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    protected String valueOf(By locator) {
        String v = visible(locator).getAttribute("value");
        return v == null ? "" : v.trim();
    }

    protected By byText(String text) {
        return By.xpath("//*[normalize-space(text())=\"" + text + "\"]");
    }

    protected By byContainsText(String text) {
        return By.xpath("//*[contains(normalize-space(text()),\"" + text + "\")]");
    }
}