package net.progress.helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverHelper {
    public static WebDriver getDriver() {
        return getDriver(false);
    }

    public static WebDriver getDriver(boolean isHeadless) {
        System.setProperty("webdriver.gecko.driver", "gecko/geckodriver.exe");
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("marionatte", isHeadless);
        FirefoxOptions options = new FirefoxOptions(dc);
        options.setHeadless(isHeadless);
        WebDriver driver = new FirefoxDriver(options);

        return driver;
    }
}
