package net.progress.helper;

import org.openqa.selenium.WebElement;

public class CurrencyFormat {
    public static double reformatPrice(WebElement price) {
        return Double.parseDouble(price.getText().substring(1).trim());
    }
}
