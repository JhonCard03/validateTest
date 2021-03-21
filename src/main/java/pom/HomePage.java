package pom;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

import static java.lang.Thread.sleep;

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

    @AfterMethod
    public void back() throws InterruptedException {

        System.out.println("devolviendo");
        WebElement collect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[2]/a")));
        collect.click();

        WebElement channels = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[2]/ul/li[2]/a")));
        channels.click();

    }

    @AfterTest
    public void quit() throws InterruptedException {
        System.out.println("cerrado");

    }


    @Test
    public void createCountry() throws InterruptedException {

        wait = new WebDriverWait(driver,60);

        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/a/span")));
        home.click();

        WebElement countries = wait.until((ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/ul/li[1]/a"))));
        countries.click();


        WebElement addCountry =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable_wrapper\"]/div[2]/div[1]/a")));
        addCountry.click();

        WebElement editCountry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[1]/a[1]")));
        editCountry.click();

        WebElement addCode = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[2]/span/div/form/div/div[1]/div/input")));
        addCode.click();
        addCode.sendKeys("415615");

        WebElement addName = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"s2id_autogen2\"]")));
        addName.click();                                                                    //*[@id="s2id_autogen2532"]
        addName.sendKeys("Jhonatans country");
        addName.sendKeys(Keys.ENTER);


        WebElement saveCountry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[1]/a[3]")));
        saveCountry.click();


        WebElement vm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"smallbox1\"]/div[1]/p/i[2]")));
        String vmValue = vm.getText();
        if(vm.getText().contains("Code or Name already in use")){
            String val = String.valueOf(addCode.isEnabled());
            WebElement deleteCountry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[1]/a[4]")));
            deleteCountry.click();
            Assert.assertEquals(val,"true");
            Assert.assertEquals(vmValue,"Code or Name already in use.");
        }else{
            WebElement resultCode = driver.findElement(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[2]/a"));
            resultCode.click();

            String expect = "415615";
            String val = resultCode.getText();
            Assert.assertEquals(vmValue,"Country saved with success!");
            Assert.assertEquals(val,expect);
        }

    }

    @Test
    private void deleteCountry(){

        wait = new WebDriverWait(driver,60);

        System.out.println("eliminando");

        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/a/span")));
        home.click();

        WebElement countries = wait.until((ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/ul/li[1]/a"))));
        countries.click();



        WebElement deleteCountry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[1]/a[4]")));
        deleteCountry.click();

        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(By.id("save_btn")));
        confirm.click();

        WebElement vm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"smallbox1\"]/div[1]/p/i[2]")));
        String vmValue = vm.getText();
        //Alert al = wait.until(ExpectedConditions.alertIsPresent());
        //String ale = al.getText();
        // System.out.println("alerta: "+ale);




        if(vm.getText().contains("Cannot delete or update, because there are groups registered with this country.")){
            System.out.println("entró if");
            Assert.assertEquals(vmValue,"Cannot delete or update, because there are groups registered with this country.");



        }else{
            System.out.println("entró else");
           Assert.assertEquals(vmValue,"Country deleted with success!");
        }


    }

    @Test

    public void downloadFile(){

        wait = new WebDriverWait(driver,60);

        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/a/span")));
        home.click();

        WebElement countries = wait.until((ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/ul/li[1]/a"))));
        countries.click();



    }




}
