import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import lv.acodemy.page_object.*;
import lv.acodemy.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Sauce Demo Test")
@Feature(value="Add item to the cart")
@Listeners (AllureTestNg.class)

public class SauceDemoTest {

    ChromeDriver driver;
    WebDriverWait wait;
    ChromeOptions options;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    HeaderPage headerPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    OverviewPage overviewPage;
    FinishPage finishPage;

    private static final Logger logger = LoggerFactory.getLogger(SauceDemoTest.class);

    Faker data = new Faker();

    @BeforeMethod
    public void beforeTest() {
        options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://saucedemo.com");

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        headerPage = new HeaderPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        overviewPage = new OverviewPage(driver);
        finishPage = new FinishPage(driver);
    }

    @Test(testName = "Add item to the cart")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that item was added to the cart")

    public void addItemToTheCart() {
        Allure.step("Scenario: Add item to the cart");
        Allure.step("Step 1: User is trying to authorize");
        loginPage.authorize("standard_user", "secret_sauce");
        Allure.step("User is authorized");

        Allure.step("Step 2: Adding item to the cart");
        inventoryPage.addItemToCartByName("Onesie");
        Allure.step("Item added to the cart");

        Allure.step("Step 3: Checking if item was added to the cart");
        assertThat(headerPage.getCartBadgeText()).isEqualTo("1");
        Allure.step("Item was added to the cart");

        headerPage.getShoppingCartLink().click();
        assertThat(cartPage.getCartItems().size()).isEqualTo(1);

        wait.until(ExpectedConditions.elementToBeClickable(cartPage.getCheckoutButton()));
        cartPage.getCheckoutButton().click();
        String firstName = data.name().firstName();
        String lastName = data.name().lastName();
        String postalCode = data.address().zipCode();

        logger.info("First name: {}, last name: {}, postal code: {}", firstName, lastName, postalCode);
        checkoutPage.fillCheckoutForm(
                firstName,
                lastName,
                postalCode);
      //  assertThat(overviewPage.getPayInf().getText()).isEqualTo("SauceCard #31337");
      //  assertThat(overviewPage.getShipInf().getText()).isEqualTo("FREE PONY EXPRESS DELIVERY!");
      //  overviewPage.getFinishButton().click();
      //  logger.info("Finish order");
      //  assertThat(finishPage.getFinishOrder().getText()).isEqualTo("Thank you for your order!");
      //  logger.info("Order is completed");
       checkoutPage.completePurchase();
       assertThat(checkoutPage.getCompleteOrderHeader().getText()).isEqualTo(Constants.Messages.THANK_YOU_FOR_YOUR_ORDER);
       assertThat(checkoutPage.getCompleteOrderText().getText()).isEqualTo(Constants.Messages.ORDER_HAS_BEEN_DISPATCHED);
       Allure.step("Purchase finished");
    }

    @Test
    public void loginEmptyCredentialTest() {

    }

    @Test
    public void loginEmptyUserNameTest() {

    }

    @Test
    public void loginEmptyPasswordTest() {

    }




    @AfterMethod()
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}