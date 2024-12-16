package utils;

import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try {
            String path = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties file at " +
                    System.getProperty("user.dir") + "/src/test/resources/config.properties", e);
        }
    }

    public void validateConfig() {
        String[] requiredKeys = {
                "usernameField", "passwordField", "continueButton", "url", "username", "password"
        };
        for (String key : requiredKeys) {
            if (properties.getProperty(key) == null) {
                throw new RuntimeException("Missing required key: " + key);
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public By getLocator(String key) {
        String locator = properties.getProperty(key);
        if (locator == null) {
            throw new IllegalArgumentException("Locator not found for key: " + key);
        }
        if (locator.startsWith("id=")) {
            return By.id(locator.substring(3));
        } else if (locator.startsWith("css=")) {
            return By.cssSelector(locator.substring(4));
        } else if (locator.startsWith("xpath=")) {
            return By.xpath(locator.substring(6));
        } else {
            throw new IllegalArgumentException("Invalid locator format: " + locator);
        }
    }

    public void printAllProperties() {
        properties.forEach((key, value) -> System.out.println(key + " = " + value));
    }
}
