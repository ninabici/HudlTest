package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public WebElement waitAndFindElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitAndClick(By locator) {
        waitAndFindElement(locator).click();
    }

    public void waitAndSendKeys(By locator, String text) {
        waitAndFindElement(locator).sendKeys(text);
    }
}
