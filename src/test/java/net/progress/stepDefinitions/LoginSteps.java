package net.progress.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.progress.POMs.LoginPagePOM;
import net.progress.POMs.ShopPagePOM;
import net.progress.helper.Context;
import net.progress.helper.DriverHelper;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class LoginSteps {
    private WebDriver driver;
    private Context context;


    public LoginSteps() {
        this.driver = DriverHelper.getDriver();
        this.context = new Context();
    }

    @Given("a user is on the login page")
    public void aUserIsInLoginPage() {
        LoginPagePOM loginPagePOM = new LoginPagePOM(driver);
        context.loginPagePOM = loginPagePOM;
        loginPagePOM.load();
        loginPagePOM.isLoaded();
        context.driver = driver;
    }

    @When("the user enters credentials and press Login button")
    public void theUserEntersCredentialsAndPressLoginButton() {
        context.loginPagePOM.loginUser();
    }

    @Then("the user is successfully redirected to shop page")
    public void theUserIsSuccessfullyRedirectedToShopPage() {
        ShopPagePOM shopPagePOM = new ShopPagePOM(context.driver);
        Assert.assertEquals("The page title is not the expected one",
                "Products".toLowerCase(), shopPagePOM.getPageTitle().toLowerCase());
    }

    @When("the user enters invalid {string} credentials and press Login button")
    public void theUserEntersInvalidCredentialsAndPressLoginButton(String credentials) {
        String username = credentials.split(",")[0];
        String password = credentials.split(",")[1];
        context.loginPagePOM.loginUser(username, password);
    }

    @Then("the alert {string} message should displayed on the screen")
    public void theAlertMessageShouldDisplayedOnTheScreen(String alertMessage) {
        Assert.assertEquals(String.format("The Error message is %s, but should be %s.",
                context.loginPagePOM.getAlertMessage(), alertMessage),
                alertMessage,context.loginPagePOM.getAlertMessage());
    }


}
