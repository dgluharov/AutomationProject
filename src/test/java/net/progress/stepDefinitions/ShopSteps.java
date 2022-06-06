package net.progress.stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.progress.POMs.*;
import net.progress.helper.Context;
import net.progress.helper.DriverHelper;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopSteps {
    private WebDriver driver;
    private Context context;

    public ShopSteps() {
        this.driver = DriverHelper.getDriver();
        this.context = new Context();
    }

    @Given("An logged user is on the shop page")
    public void anLoggedUserIsOnTheShopPage() {
        context.driver = driver;
        context.loginPagePOM = new LoginPagePOM(context.driver);
        context.shopPagePOM = new ShopPagePOM(context.driver);
        context.loginPagePOM.load();
        context.loginPagePOM.loginUser();
        context.shopPagePOM.isLoaded();
    }

    @And("the user adds the following items to the cart:")
    public void theUserAddsTheFollowingItemsToTheCart(DataTable dataTable) throws InterruptedException {
        Map<String, String> items = dataTable.asMaps().get(0);
        for (String key : items.keySet()) {
            String itemName = items.get(key);
            if (itemName != null && !itemName.trim().equals("")) {
                context.shopPagePOM.selectAddToCartButton(itemName);
            }
        }
        context.items = items;
    }

    @When("the user navigates to the Checkout Page")
    public void theUserNavigatesToTheCheckoutPage() {
        context.shopPagePOM.navigateToCart();
        CartPOM cartPOM = new CartPOM(context.driver);
        cartPOM.clickCheckoutButton();
        YourInformationPOM yourInformationPOM = new YourInformationPOM(context.driver);
        yourInformationPOM.populateRequiredFields();
        yourInformationPOM.clickContinueButton();
    }

    @Then("all items are successfully added")
    public void allItemsAreSuccessfullyAdded() {
        CheckoutPOM checkoutPOM = new CheckoutPOM(context.driver);
        Map<String, String> itemsExpected = context.items;
        for (String key : itemsExpected.keySet()) {
            String bookTitle = itemsExpected.get(key);
            if (bookTitle != null && !bookTitle.equals("")) {
                Assert.assertTrue(String.format("Book with name %s is not added to the cart", bookTitle),
                        checkoutPOM.doesItemExist(bookTitle));
            }
        }
        Assert.assertEquals("The subtotal price is not equal of the sum of the prices of each product",
                checkoutPOM.calculateSubTotalPrice(), checkoutPOM.getSubTotalPrice(), 0.01);
    }

    @When("the user selects sort type {string}")
    public void theUserSelectsSort(String sortType) {
        context.products = context.shopPagePOM.getProducts(sortType);
        context.shopPagePOM.selectSortingType(sortType);
    }

    @Then("the products should correctly sorted by type {string}")
    public void theProductsShouldCorrectlySortedByType(String sortType) {
        List<String> actualSortedList = context.shopPagePOM.getProducts(sortType);
        List<String> expectedSortedList = context.shopPagePOM.sortList(context.products, sortType);
        for (int i = 0; i < actualSortedList.size(); i++) {
            Assert.assertEquals("The sorting is not as expected one",
                    expectedSortedList.get(i), expectedSortedList.get(i));
        }
    }


}
