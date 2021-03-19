package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HomePage {
    String page = "https://amrs-dev.engkantar.com";
    WebDriver driver;
    String pathChrome =  System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
    WebDriverWait wait;


    @BeforeTest
    public void loginK(){

        String user =  "testqa";
        String password = "#testqa@";
        System.setProperty("webdriver.chrome.driver",pathChrome);
        driver = new ChromeDriver();
        driver.get(page);
        driver.manage().window().maximize();


         wait = new WebDriverWait(driver,60);

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

    @Test
    public void createCountry(){
        wait = new WebDriverWait(driver,60);

        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/a/span")));
        home.click();

        WebElement countries = wait.until((ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/ul/li[1]/a"))));
        countries.click();

        WebElement addCountry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable_wrapper\"]/div[2]/div[1]/a")));
        addCountry.click();

        WebElement editCountry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[1]/a[1]")));
        editCountry.click();

        WebElement addCode = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[2]/span/div/form/div/div[1]/div/input")));
        addCode.click();
        addCode.sendKeys("415615");

        WebElement addName = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"s2id_autogen2\"]")));
        addName.click();                                                                    //*[@id="s2id_autogen2532"]
        addName.sendKeys("Prueba nombre");
        addName.sendKeys(Keys.ENTER);

        WebElement saveCountry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[1]/a[3]")));
        saveCountry.click();


    }

}
