import com.github.javafaker.Faker;
import lv.acodemy.page_object.*;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import  org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SauceDemoTest {
    ChromeDriver driver;
    ChromeOptions options;

    LoginPage loginPage;
    InventoryPage inventoryPage;
    HeaderPage headerPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    Faker data = new Faker();


    @BeforeMethod
    public void beforeTest() {

        options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);


        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com");

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        headerPage = new HeaderPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void verifyLoggedInTest() {
        loginPage.authorize("standard_user", "secret_sauce");

        String productsText = driver.findElement(By.className("title")).getText();
        Assertions.assertThat(productsText)
                .withFailMessage("Are u good? Expected title to be 'Products' but was '%s'", productsText)
                .isNotNull()
                .isNotEmpty()
                .startsWith("Prod")
                .endsWith("ucts")
                .isEqualTo("Products");
    }

    @Test
    public void LogInTest ( )
    {
        loginPage.authorize("standard_user", "secret_sauce");
    }

    @Test
    public void addItemToTheCart(){
        loginPage.authorize("standard_user", "secret_sauce");
        inventoryPage.addItemToCartByName("Onesie");
        Assertions.assertThat(headerPage.getCartBadgeText()).isEqualTo("1");

        headerPage.getShoppingCartLink().click();
        Assertions.assertThat(cartPage.getCartItems().size()).isEqualTo(1);

        cartPage.getCheckoutButton().click();

        checkoutPage.fillCheckoutForm(
               data.name().firstName(),
               data.name().lastName(),
               data.address().zipCode());

        System.out.println("123");

    }

    @AfterMethod()
    public void tearDown(){
      driver.close();
      driver.quit();

    }

}
