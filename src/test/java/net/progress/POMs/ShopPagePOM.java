package net.progress.POMs;

import net.progress.helper.Context;
import net.progress.helper.CurrencyFormat;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShopPagePOM extends LoadableComponent {
    private final WebDriver driver;
    private final String url = "https://www.saucedemo.com/inventory.html";

    @FindBy(how = How.CSS, using = ".title")
    private WebElement pageTitle;

    @FindBy(how = How.CSS, using = ".inventory_item")
    private List<WebElement> productsList;

    @FindBy(how = How.ID, using = "shopping_cart_container")
    private WebElement navigateToCartButton;

    @FindBy(how = How.CSS, using = ".product_sort_container")
    private WebElement sortingDropdown;

    @FindBy(how = How.CSS, using = ".inventory_item_price")
    private List<WebElement> productPricesList;
    @FindBy(how = How.CSS, using = ".inventory_item_name")
    private List<WebElement> productNamesList;


    public ShopPagePOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public void selectAddToCartButton(String itemName) throws InterruptedException {
        WebElement result = null;
        for (WebElement product : productsList) {
            WebElement productName = product.findElement(By.cssSelector(".inventory_item_name"));
            if (productName.getText().equalsIgnoreCase(itemName)) {
                result = product.findElement(By.cssSelector(".btn"));
            }
        }
        Assert.assertNotNull(String.format("There is no item with name %s", itemName), result);
        result.click();
        Thread.sleep(200);
    }

    public List<String> getProductPrices() {
        List<String> result = new ArrayList<>();
        for (WebElement webElement : productPricesList) {
            result.add(String.valueOf(CurrencyFormat.reformatPrice(webElement)));
        }
        return result;
    }

    public List<String> getProductNames() {
        List<String> result = new ArrayList<>();
        for (WebElement webElement : productNamesList) {
            result.add(webElement.getText());
        }
        return result;
    }

    public void navigateToCart() {
        navigateToCartButton.click();
    }

    public void selectSortingType(String sortType) {
        Select select = new Select(sortingDropdown);
        select.selectByVisibleText(sortType);
    }

    public List<String> getProducts(String sortType) {
        List<String> result = null;
        if (sortType.equals("Price (low to high)") || sortType.equals("Price (high to low)")) {
            result = getProductPrices();
        } else {
            result = getProductNames();
        }
        return result;
    }

    @Override
    protected void load() {
        driver.get(this.url);
    }

    @Override
    public void isLoaded() throws Error {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(pageTitle));
    }

    public List<String> sortList(List<String> products, String sortType) {
        List<String> result = null;
        if (sortType.equals("Price (low to high)") || sortType.equals("Name (A to Z)")) {
            result = products.stream().sorted().toList();
        } else {
            result = products.stream().sorted(Comparator.reverseOrder()).toList();
        }
        return result;
    }
}
