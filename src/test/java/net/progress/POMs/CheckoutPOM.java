package net.progress.POMs;

import net.progress.helper.CurrencyFormat;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutPOM extends LoadableComponent {
    private final WebDriver driver;
    private final String url = "https://www.saucedemo.com/checkout-step-two.html";
    @FindBy(how = How.CSS, using = ".title")
    private WebElement pageTitle;

    @FindBy(how = How.CSS, using = ".cart_item")
    private List<WebElement> checkoutItems;

    @FindBy(how = How.CSS, using = ".summary_subtotal_label")
    private WebElement subTotal;

    @FindBy(how = How.CSS, using = ".inventory_item_price")
    private List<WebElement> itemPricesList;


    public CheckoutPOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean doesItemExist(String bookTitle) {
        boolean result = false;
        for (WebElement webElement : checkoutItems) {
            if (webElement.getText().contains(bookTitle)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public double getSubTotalPrice() {
        double result = 0.00;
        result = Double.parseDouble(subTotal.getText().substring(13));
        return result;
    }

    public double calculateSubTotalPrice() {
        double result = 0.00;
        for (WebElement webElement : itemPricesList) {
            result += CurrencyFormat.reformatPrice(webElement);
        }
        return result;
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
