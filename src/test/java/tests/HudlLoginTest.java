package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;

public class HudlLoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ConfigReader configReader;

    @BeforeClass
    public void setUpClass() throws InterruptedException {
        // Initialize the WebDriver
        driver = new ChromeDriver();

        // Set implicit wait globally for all elements
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        // Initialize ConfigReader
        configReader = new ConfigReader();
        configReader.validateConfig(); // Validate required keys

        // Maximize browser window
        driver.manage().window().maximize();

        // Initialize Page Objects
        loginPage = new LoginPage(driver, configReader);
        dashboardPage = new DashboardPage(driver, configReader);
    }

    @BeforeMethod
    public void setUpMethod() {
        try {
            //Clear cookies and storage
            driver.manage().deleteAllCookies();
//        // Clear local storage
//        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
//        ((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
//        System.out.println("Local and session storage cleared.");
        } catch (Exception e) {
            System.err.println("Failed to clear local or session storage: " + e.getMessage());
            // Log the error but continue with the test
        }
        // Navigate to the home page
        driver.get(configReader.getProperty("url"));

        // Log where the test is starting
        System.out.println("Opened URL: " + driver.getCurrentUrl());

        // Open login menu and select login menu item in order for tests to start on Login page
        loginPage.clickLoginButton();
        loginPage.clickLoginMenuItem();
    }

    /**
     * Test to verify that user can login with valid credentials - both valid username and valid matching password
     */
    @Test
    public void loginSuccess() {
        System.out.println("Starting loginSuccess test...");

        // Locate username field and set the value
        WebElement usernameField = driver.findElement(configReader.getLocator("usernameField"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Focus the element and set the value directly
        js.executeScript("arguments[0].focus();", usernameField);
        js.executeScript("arguments[0].value='" + configReader.getProperty("username") + "';", usernameField);

        loginPage.clickContinue();

        // Enter password
        loginPage.enterPassword(configReader.getProperty("password"));
        loginPage.clickContinue2();

        // Verify login by checking the user display name on the Dashboard page
        String userInitials = dashboardPage.getUserInitials();
        Assert.assertEquals(userInitials, configReader.getProperty("userInitials"),
                "Expected user initials '" + configReader.getProperty("userInitials") + "', but found '" + userInitials + "'");
        //Logout user
        loginPage.forceLogout();
    }


    /**
     * Test to verify that user can use Continue with Google button to sign in
     */
    @Test
    public void googleLogin() {
        System.out.println("Starting googleLogin test...");

        // Verify the Google login button is present
        Assert.assertTrue(loginPage.isGoogleLoginButtonPresent(), "Google login button is not present.");

        // Click the Google login button
        loginPage.clickGoogleLoginButton();

        // Switch to the new tab or handle redirection
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        // Verify the URL contains the expected Google login domain
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("accounts.google.com"),
                "Expected Google login page, but found: " + currentUrl);
    }

    /**
     * Test to verify that user can use Continue with Facebook button to sign in
     */
    @Test
    public void facebookLogin() {
        System.out.println("Starting facebookLogin test...");

        // Verify the Facebook login button is present
        Assert.assertTrue(loginPage.isFacebookLoginButtonPresent(), "Facebook login button is not present.");

        // Click the Google login button
        loginPage.clickFacebookLoginButton();

        // Switch to the new tab or handle redirection
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        // Verify the URL contains the expected Facebook login domain
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("facebook.com/login.php"),
                "Expected Facebook login page, but found: " + currentUrl);
    }

    /**
     * Test to verify that user can use Continue with Apple button to sign in
     */
    @Test
    public void appleLogin() {
        System.out.println("Starting appleLogin test...");

        // Verify the Apple login button is present
        Assert.assertTrue(loginPage.isAppleLoginButtonPresent(), "Apple login button is not present.");

        // Click the Google login button
        loginPage.clickAppleLoginButton();

        // Switch to the new tab or handle redirection
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        // Verify the URL contains the expected Apple login domain
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("appleid.apple.com"),
                "Expected Apple login page, but found: " + currentUrl);
    }

    /**
     * Test to verify that user can create new account from the login page
     */
    @Test
    public void createAccountLinkLoginPage() {
        System.out.println("Starting createAccountLinkLoginPage test...");

        // Verify the Create Account link is present
        Assert.assertTrue(loginPage.isCreateAccountLinkPresent(), "Create Account link is not present.");

        // Click the Create Account ccount link
        loginPage.clickCreateAccountLink();

        // Switch to the new tab or handle redirection
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        // Verify the title of the page is Create Account
        String currentPageTitle = driver.getTitle();
        Assert.assertTrue(currentPageTitle.contains("Create Account"),
                "Expected Create New Account page, but found: " + currentPageTitle);
    }

    /**
     * Test To verify that Privacy policy link is present on the login page an page loads. Conents of that page are out of scope for this test
     */
    @Test
    public void privacyPolicyLink() {
        System.out.println("Starting privacyPolicyLink test...");
        // Verify the Privacy Policy link is present
        Assert.assertTrue(loginPage.isPrivacyPolicyLinkPresent(), "Create Account link is not present.");

        // Click the Create Account ccount link
        loginPage.clickPrivacyPolicyLink();

        // Switch to the new window/tab
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        // Assert on the title of the new tab(simulteniously checks that link opens in new tab when pressed)
        String currentPageTitle = driver.getTitle();
        Assert.assertTrue(currentPageTitle.contains("Hudl Privacy Policy"),
                "Expected Privacy Policy page, but found: " + currentPageTitle);

        // Assert on the URL of the new window/tab
        String currentPageUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentPageUrl.contains("privacy"),
                "URL does not match expected Privacy Policy URL. Found: " + currentPageUrl);

        // Close the new window/tab and return to the original
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    /**
     * Test that Terms of Service link is present on login page and opens the Terms of Service page
     */
    @Test
    public void termsOfServiceLink() {
        System.out.println("Starting termsOfServiceLink test...");
        // Verify the Terms of Service link is present
        Assert.assertTrue(loginPage.isTermsOfServiceLinkPresent(), "Terms of Service link is not present.");

        // Click the Create Account ccount link
        loginPage.termsOfServiceLink();

        // Switch to the new window/tab
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        // Assert on the title of the new tab(simulteniously checks that link opens in new tab when pressed)
        String currentPageTitle = driver.getTitle();
        Assert.assertTrue(currentPageTitle.contains("Hudl Site Terms"),
                "Expected Hudl Site Terms page, but found: " + currentPageTitle);

        // Assert on the URL of the new tab
        String currentPageUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentPageUrl.contains("hudl.com/terms"),
                "URL does not match expected Hudl Site Terms URL. Found: " + currentPageUrl);

        // Close the new window/tab and return to the original
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    /**
     * Test to verify forgotten password option is available
     */
    @Test
    public void forgottenPasswordLink() {
        System.out.println("Starting forgottenPasswordLink test...");
        // Locate username field and set the value
        WebElement usernameField = driver.findElement(configReader.getLocator("usernameField"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Focus the element and set the value directly
        js.executeScript("arguments[0].focus();", usernameField);
        js.executeScript("arguments[0].value='" + configReader.getProperty("username") + "';", usernameField);
        //Click Continue button under email field
        loginPage.clickContinue();
        //Click on Forgotten Password link at the password entering frame
        loginPage.forgottenPasswordLink();
        //Verfy that user landed on reset password page by checking that message about resting password is displayed
        String currentPageUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentPageUrl.contains("identity.hudl.com/u/reset-password/"),
                "URL does not match expected Hudl Site Terms URL. Found: " + currentPageUrl);

    }

    /**
     * Test to verify that invalid format of email address is not accepted by the username field
     */
    @Test
    public void invalidEmailFormat() {
        System.out.println("Starting invalidEmailFormat test...");

        // Locate username field and set the value
        WebElement usernameField = driver.findElement(configReader.getLocator("usernameField"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Focus the element and set the value directly
        js.executeScript("arguments[0].focus();", usernameField);

        // Ensure the string is properly quoted
        js.executeScript("arguments[0].value='john.smith'", usernameField);
        loginPage.clickContinue();

        //Locate the error message element
        WebElement errorElement = driver.findElement(configReader.getLocator("emailErrorElement"));

        // Extract the error message text
        String errorMessage = errorElement.getText().trim();

        // Log the extracted message for debugging
        System.out.println("Extracted Error Message: " + errorMessage);

        // Assert the error message
        Assert.assertEquals(errorMessage, configReader.getProperty("emailErrorText"), "Error message did not match.");

    }

    /**
     * Test to verify that non-registered user cannot login, or even proceed to password frame
     */
    @Test
    public void userNotExistingAttempt() {
        System.out.println("Starting userNotExistingAttempt test...");

        // Locate username field and set the value
        WebElement usernameField = driver.findElement(configReader.getLocator("usernameField"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Focus the element and set the value directly
        js.executeScript("arguments[0].focus();", usernameField);

        // Ensure the string is properly quoted
        js.executeScript("arguments[0].value='john.smith@anydomain.com'", usernameField);
        loginPage.clickContinue();

        // Enter wrong password
        loginPage.enterPassword(configReader.getProperty("wrongPassword"));
        loginPage.clickContinue2();

        //Locate the error message element
        WebElement errorElement = driver.findElement(configReader.getLocator("passwordErrorElement"));

        // Extract the error message text
        String errorMessage = errorElement.getText().trim();

        // Log the extracted message for debugging
        System.out.println("Extracted Error Message: " + errorMessage);

        // Assert the error message
        Assert.assertEquals(errorMessage, configReader.getProperty("passwordErrorText"), "Error message did not match.");
    }


    /**
     * Test to verify that registered user cannot login with wrong password
     */
    @Test
    public void wrongPasswordAttempt() {
        System.out.println("Starting wrongPasswordAttempt test...");

        // Locate username field and set the value
        WebElement usernameField = driver.findElement(configReader.getLocator("usernameField"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Focus the element and set the value directly
        js.executeScript("arguments[0].focus();", usernameField);
        js.executeScript("arguments[0].value='" + configReader.getProperty("username") + "';", usernameField);

        //Click Continue button
        loginPage.clickContinue();

        // Enter wrong password
        loginPage.enterPassword(configReader.getProperty("wrongPassword"));
        loginPage.clickContinue2();

        //Locate the error message element
        WebElement errorElement = driver.findElement(configReader.getLocator("passwordErrorElement"));

        // Extract the error message text
        String errorMessage = errorElement.getText().trim();

        // Log the extracted message for debugging
        System.out.println("Extracted Error Message: " + errorMessage);

        // Assert the error message
        Assert.assertEquals(errorMessage, configReader.getProperty("existingUserWrongPassword"), "Error message did not match.");
    }

    /**
     * Test to verify that when user enters nothing in the email field, error po-up is dipslayed anduser is not taken to the enter passweord frame
     */
    @Test
    public void noUsernameError() {
        System.out.println("Starting noUsernameError test...");

        // Locate username field and set the value
        WebElement usernameField = driver.findElement(configReader.getLocator("usernameField"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Focus the element and set the value directly
        js.executeScript("arguments[0].focus();", usernameField);
        js.executeScript("arguments[0].value='';", usernameField);
        //Click Continue button
        loginPage.clickContinue();
        //Assert popup message 'Please fill out this field.'
        String validationMessage = (String) js.executeScript(
                "return arguments[0].validationMessage;", usernameField);

        // Log the extracted message for debugging
        System.out.println("Validation Message: " + validationMessage);

        // Retrieve the expected message from the properties file
        String expectedMessage = configReader.getProperty("popup");

        // Assert the validation message
        Assert.assertEquals(validationMessage, expectedMessage, "Error message did not match.");
    }

    /**
     * Test to verify that after entering valid email address, user cannot be logged in by leaving the password field empty
     */
    @Test
    public void emptyPassword() {
        System.out.println("Starting emptyPassword test...");

        //Locate username field and set the value
        WebElement usernameField = driver.findElement(configReader.getLocator("usernameField"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Focus the element and set the value directly
        js.executeScript("arguments[0].focus();", usernameField);
        js.executeScript("arguments[0].value='" + configReader.getProperty("username") + "';", usernameField);

        //Click Continue button
        loginPage.clickContinue();

        //Enter wrong password
        loginPage.enterPassword("");
        loginPage.clickContinue2();
        //Assert popup message 'Please fill out this field.'
        //Locate password field and fetch validation message
        WebElement passwordField = driver.findElement(configReader.getLocator("passwordField"));
        String validationMessage = (String) js.executeScript(
                "return arguments[0].validationMessage;", passwordField);

        //Log the extracted message for debugging
        System.out.println("Validation Message: " + validationMessage);

        //Retrieve the expected message from the properties file
        String expectedMessage = configReader.getProperty("popup");

        //Assert the validation message
        Assert.assertEquals(validationMessage, expectedMessage, "Error message did not match.");
    }

    /**
     * Test to verify that password is case sensitive and user cannot login if password is entered all lowercase
     */
    @Test
    public void caseSensitivePassword() {
        System.out.println("Starting wrongPasswordAttempt test...");

        // Locate username field and set the value
        WebElement usernameField = driver.findElement(configReader.getLocator("usernameField"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Focus the element and set the value directly
        js.executeScript("arguments[0].focus();", usernameField);
        js.executeScript("arguments[0].value='" + configReader.getProperty("username") + "';", usernameField);

        //Click Continue button
        loginPage.clickContinue();

        //Enter lowercase password
        loginPage.enterPassword(configReader.getProperty("lowercasePassword"));
        loginPage.clickContinue2();

        //Locate the error message element
        WebElement errorElement = driver.findElement(configReader.getLocator("passwordErrorElement"));

        // Extract the error message text
        String errorMessage = errorElement.getText().trim();

        // Log the extracted message for debugging
        System.out.println("Extracted Error Message: " + errorMessage);

        // Assert the error message
        Assert.assertEquals(errorMessage, configReader.getProperty("existingUserWrongPassword"), "Error message did not match.");
    }

    /**
     * Test to verify that deactivated user cannot login with their previously valid credentials
     */
    @Test
    public void deactivatedUserLogin() {
//TODO Identify deactivated user in the database and the details into the config file.
// Then add the login action with those parameters and add assertion that error message is dislayed:
// "Your account has been deactivated. Please contact your administrator"
        //Add login logic

        //Add error assertion logic
    }

    @AfterMethod
    public void tearDownMethod() {
        // Clear browser cookies to reset the session state
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void tearDownClass() {
        // Quit the browser after all tests
        if (driver != null) {
            driver.quit();
        }
    }

}


