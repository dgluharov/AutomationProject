package net.progress.POMs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class LoginPagePOM extends LoadableComponent {

    private final WebDriver driver;

    @FindBy(how = How.CSS, using = ".login_logo")
    private WebElement loginLogo;
    @FindBy(how = How.ID, using = "user-name")
    private WebElement usernameField;
    @FindBy(how = How.ID, using = "password")
    private WebElement passwordField;
    @FindBy(how = How.ID, using = "login-button")
    private WebElement loginButton;
    @FindBy(how = How.CSS, using = ".login_credentials")
    private WebElement usernames;
    @FindBy(how = How.CSS, using = ".login_password")
    private WebElement password;
    @FindBy(how = How.CSS, using = ".error-message-container > h3")
    private WebElement errorMessage;

    public LoginPagePOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void loginUser() {
//        usernameField.sendKeys(getRandomUsername());
        usernameField.sendKeys(getUsername());
        passwordField.sendKeys(getPassword());
        loginButton.click();
    }

    public void loginUser(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public String getRandomUsername() {
        String username;
        Random random = new Random();
        String[] usernamesArray = usernames.getText().split("\\R");
        username = usernamesArray[random.nextInt(1, 4)];
        return username;
    }

    public String getUsername() {
        String username;
        String[] usernamesArray = usernames.getText().split("\\R");
        username = usernamesArray[1];
        return username;
    }

    public String getPassword() {
        String[] tempArray = password.getText().split("\\R");
        return tempArray[1];
    }

    public String getAlertMessage() {
        return errorMessage.getText();
    }


    @Override
    public void load() {
        driver.get("https://www.saucedemo.com/");
    }

    @Override
    public void isLoaded() throws Error {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(loginLogo));
    }
}
