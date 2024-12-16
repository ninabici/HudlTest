package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final ConfigReader configReader;

    public LoginPage(WebDriver driver, ConfigReader configReader) {
        this.driver = driver;
        this.configReader = configReader;
    }

    //Click Login button from homepage
    public void clickLoginButton() {
        driver.findElement(configReader.getLocator("loginButtonHomepage")).click();
    }

    //Click Hudl logo item from login drop-down
    public void clickLoginMenuItem() {
        driver.findElement(configReader.getLocator("loginMenuItem")).click();
    }

    //Enter password in Password field
    public void enterPassword(String password) {
        driver.findElement(configReader.getLocator("passwordField")).sendKeys(password);
    }

    //Click continue button on email page
    public void clickContinue() {
        driver.findElement(configReader.getLocator("continueButton")).click();
    }

    //Click Continue button after password has been entered
    public void clickContinue2() {
        driver.findElement(configReader.getLocator("continueButton2")).click();
    }

    //Check if Continue with Google button is present on page
    public boolean isGoogleLoginButtonPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(configReader.getLocator("googleButton"))).isDisplayed();
    }

    //Click Continue with Google button
    public void clickGoogleLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement googleButton = wait.until(ExpectedConditions.elementToBeClickable(configReader.getLocator("googleButton")));
        googleButton.click();
    }

    //Check if Continue with Facebook button is present on page
    public boolean isFacebookLoginButtonPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(configReader.getLocator("facebookButton"))).isDisplayed();
    }

    //Click Continue with Facebook button
    public void clickFacebookLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement facebookButtonButton = wait.until(ExpectedConditions.elementToBeClickable(configReader.getLocator("facebookButton")));
        facebookButtonButton.click();
    }

    //Check if Continue with Apple button is present on page
    public boolean isAppleLoginButtonPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(configReader.getLocator("appleButton"))).isDisplayed();
    }

    //Click Continue with Apple button
    public void clickAppleLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement appleButtonButton = wait.until(ExpectedConditions.elementToBeClickable(configReader.getLocator("appleButton")));
        appleButtonButton.click();
    }

    //Check if Create Account link is present on page
    public boolean isCreateAccountLinkPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(configReader.getLocator("createAccountLink"))).isDisplayed();
    }

    //Click Create Account Link
    public void clickCreateAccountLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement appleButtonButton = wait.until(ExpectedConditions.elementToBeClickable(configReader.getLocator("createAccountLink")));
        appleButtonButton.click();
    }

    //Check if Privacy Policy link is present on page
    public boolean isPrivacyPolicyLinkPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(configReader.getLocator("privacyPolicyLink"))).isDisplayed();
    }

    //Click Privacy Policy link
    public void clickPrivacyPolicyLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement privacyPolicyLink = wait.until(ExpectedConditions.elementToBeClickable(configReader.getLocator("privacyPolicyLink")));
        privacyPolicyLink.click();
    }

    //Force user to logout by entering logout url in the browser
    public void forceLogout() {
        // Login user out by force landing on logout page
        driver.get(configReader.getProperty("logoutUrl"));
        System.out.println("Forced logout by navigating to logout URL.");
    }

    //Check if Terms of Service link is present on page
    public void termsOfServiceLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement termsOfServiceLink = wait.until(ExpectedConditions.elementToBeClickable(configReader.getLocator("termsOfServiceLink")));
        termsOfServiceLink.click();
    }

    //Click Terms of Service link
    public boolean isTermsOfServiceLinkPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(configReader.getLocator("termsOfServiceLink"))).isDisplayed();
    }

    //Click Forgotten Password link
    public void forgottenPasswordLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement termsOfServiceLink = wait.until(ExpectedConditions.elementToBeClickable(configReader.getLocator("forgottenPasswordLink")));
        termsOfServiceLink.click();
    }
}
