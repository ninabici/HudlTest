package pages;

import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class DashboardPage {
    private final WebDriver driver;
    private final ConfigReader configReader;

    public DashboardPage(WebDriver driver, ConfigReader configReader) {
        this.driver = driver;
        this.configReader = configReader;
    }

    public String getUserInitials() {
        return driver.findElement(configReader.getLocator("userAccountMenuItem")).getText();
    }
}
