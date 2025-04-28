import lv.acodemy.page_object.InventoryPage;
import lv.acodemy.page_object.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import  org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SauceDemoTest {
    ChromeDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;

    @BeforeMethod
    public void beforeTest() {

        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    public void verifyLoggedInTest(){
       loginPage.authorize("standard_user","secret_sauce");
    // Chrome driver;

        String productText= driver.findElement(By.className("title")).getText();
        assertThat(productText).isEqualTo("Products");
        assertThat(productText)
                .isNotEmpty()
                .isNotNull()
                .startsWith("Prod")
                .endsWith("ucts");


    }

    @Test
    public void LogInTest ( )
    {
          loginPage.authorize("standard_user", "secret_sauce");
    }

    @Test
    public void addItemToTheCart(){
        loginPage.authorize("standard_user", "secret_sauce");
        inventoryPage.addItemToTheCartByName("Onesie");

    }

    @AfterMethod()
    public void tearDown(){
      driver.close();
      driver.quit();

    }

}
