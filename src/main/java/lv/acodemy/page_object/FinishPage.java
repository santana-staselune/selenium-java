package lv.acodemy.page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FinishPage {
    public FinishPage (ChromeDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy (className = "complete-header")
    private WebElement finishOrder;

    public WebElement getFinishOrder(){
        return finishOrder;

    }
}
