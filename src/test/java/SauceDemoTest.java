import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import  org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SauceDemoTest {
    ChromeDriver driver;

    @BeforeMethod
    public void beforeTest(){
        driver = new ChromeDriver();
        //How to call driver methods?
        // URL: www.saucedemo.com
        driver.get("https://www.saucedemo.com");
        // Need to find element, name input field?
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        // hello -> char sequence h e l l o
        //By.id()
        //By.name();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

    }

    @Test
    public void verifyLoggedInTest(){

    // Chrome driver;

        String productText= driver.findElement(By.className("title")).getText();
        assertThat(productText).isEqualTo("Products");
        assertThat(productText)
                .isNotEmpty()
                .isNotNull()
                .startsWith("Prod")
                .endsWith("ucts");


    }

    @AfterMethod()
    public void tearDown(){
        driver.close();
        driver.quit();

    }

}
