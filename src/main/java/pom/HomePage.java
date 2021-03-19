package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;

public class HomePage {
    String page = "https://amrs-dev.engkantar.com";
    WebDriver driver;
    String pathChrome =  System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";



    @BeforeTest
    public void loginK(){

        String user =  "testqa";
        String password = "#testqa@";
        System.setProperty("webdriver.chrome.driver",pathChrome);
        driver = new ChromeDriver();
        driver.get(page);
        driver.manage().window().maximize();


        WebDriverWait wait = new WebDriverWait(driver,60);

        WebElement usr = wait.until(ExpectedConditions.elementToBeClickable(By.name("user")));
        usr.clear();
        usr.click();
        usr.sendKeys(user);

        WebElement pwd = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        pwd.clear();
        pwd.click();
        pwd.sendKeys(password);

        WebElement conectar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login-form\"]/footer/button")));
        conectar.click();

    }

}
