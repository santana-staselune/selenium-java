package lv.acodemy.page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OverviewPage {
    public OverviewPage (ChromeDriver driver) {
        PageFactory.initElements(driver, this);
    }
@FindBy (css = ".btn_action.cart_button")
    private WebElement finishButton;

    public WebElement getFinishButton() {
        return finishButton;
    }
    }

