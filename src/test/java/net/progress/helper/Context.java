package net.progress.helper;

import io.cucumber.java.eo.Do;
import net.progress.POMs.LoginPagePOM;
import net.progress.POMs.ShopPagePOM;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public class Context {
    public LoginPagePOM loginPagePOM;
    public WebDriver driver;
    public ShopPagePOM shopPagePOM;
    public Map<String, String> items;
    public List<String> products;
}
