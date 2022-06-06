package net.progress.POMs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPOM extends LoadableComponent {
    private final WebDriver driver;
    private final String url = "https://www.saucedemo.com/cart.html";

    @FindBy(how = How.CSS, using = ".title")
    private WebElement pageTitle;

    @FindBy(how = How.ID, using = "checkout")
    private WebElement checkoutButton;

    public CartPOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCheckoutButton(){
        checkoutButton.click();
    }

    @Override
    public void load() {
        driver.get(this.url);
    }

    @Override
    public void isLoaded() throws Error {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(pageTitle));
    }
}
