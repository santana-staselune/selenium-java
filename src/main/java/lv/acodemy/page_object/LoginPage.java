package lv.acodemy.page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {



    public LoginPage(ChromeDriver driver) {
           PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, id="user-name")
    private WebElement inputFieldUserName;

    @FindBy(how = How.ID, id="password")
    private WebElement inputFieldPassword;

    @FindBy(how = How.ID, id = "login-button")
    private WebElement inputFieldLogin;



    public void authorize(String username, String password){

    inputFieldUserName.sendKeys(username);
    inputFieldPassword.sendKeys(password);
    inputFieldLogin.click();

    }
}