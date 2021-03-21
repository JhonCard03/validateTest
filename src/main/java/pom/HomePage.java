package pom;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import util.FileKan;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class HomePage {
    String page = "https://amrs-dev.engkantar.com";
    WebDriver driver;
    String pathChrome =  System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
    WebDriverWait wait;


   @BeforeTest
    public void loginK(){
        HashMap<String, Object> chromeConfig = new HashMap<String, Object>();
        chromeConfig.put("profile.default_content_settings.popups",0);
        chromeConfig.put("download.default_directory","");
        ChromeOptions co = new ChromeOptions();
        co.setExperimentalOption("prefs",chromeConfig);

        String user =  "testqa";
        String password = "#testqa@";
        System.setProperty("webdriver.chrome.driver",pathChrome);


        driver = new ChromeDriver(co);
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

   // @AfterMethod
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

        Thread.sleep(3000);
        WebElement vm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[starts-with(@id,\"smallbox\")]")));
        String vmValue = vm.getText();
        if(vm.getText().contains("ERROR!\n" +
                "Code or Name already in use.")){
            String val = String.valueOf(addCode.isEnabled());
            WebElement deleteCountry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[1]/a[4]")));
            deleteCountry.click();
            Assert.assertEquals(val,"true");
            Assert.assertEquals(vmValue,"ERROR!\n" +
                    "Code or Name already in use.");
        }else{
            WebElement resultCode = driver.findElement(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[2]/a"));
            resultCode.click();

            String expect = "415615";
            String val = resultCode.getText();
            Assert.assertEquals(vmValue,"SUCCESS!\n" +
                    "Country saved with success!");
            Assert.assertEquals(val,expect);
        }

    }

    @Test(priority = 3)
    public void deleteCountry() throws InterruptedException {

        wait = new WebDriverWait(driver,60);

        System.out.println("eliminando");

        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/a/span")));
        home.click();

        WebElement countries = wait.until((ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/ul/li[1]/a"))));
        countries.click();


        Thread.sleep(3000);
        WebElement deleteCountry = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"countryTable\"]/tbody/tr[1]/td[1]/a[4]")));
        deleteCountry.click();

        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(By.id("save_btn")));
        confirm.click();

        Thread.sleep(1500);
        WebElement vm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[starts-with(@id,\"smallbox\")]")));
        String vmValue = vm.getText();

        if(vm.getText().contains("ERROR!\n" +
                "Cannot delete or update, because there are groups registered with this country.")){
            System.out.println("entró if");
            Assert.assertEquals(vmValue,"ERROR!\n" +
                    "Cannot delete or update, because there are groups registered with this country.");

        }else{
            System.out.println("entró else");
            Assert.assertEquals(vmValue,"SUCCESS!\n" +
                    "Country deleted with success!");
        }


    }


    @Test(priority = 1)
    public void downloadFile() throws InterruptedException {

        wait = new WebDriverWait(driver,60);
        String downloadFilePath =System.getProperty("user.home")+"\\Downloads";
        System.out.println("url: "+downloadFilePath);

        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/a/span")));
        home.click();


        File f = new File(downloadFilePath);
        File [] listFilesBefore = f.listFiles();
        int b = listFilesBefore.length;
        Thread.sleep(2000);
        System.out.println("before "+ b);

        WebElement countries = wait.until((ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"left-panel\"]/nav/ul/li[1]/ul/li[1]/a"))));
        countries.click();
        Thread.sleep(2000);

        WebElement download = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ToolTables_countryTable_0\"]")));
        download.click();
        Thread.sleep(2000);
        File [] listFilesAfter = f.listFiles();
        int a = listFilesAfter.length;
        System.out.println("After "+ a);

        /*String[] actualFiles = f.list();
        List<String> countryFiles = new ArrayList<String>();


        for (int i= 0; i<actualFiles.length;i++){



            if(actualFiles[i].contains("country-2021")){
                countryFiles.add(actualFiles[i]);
            }



        }*/

        Assert.assertTrue(a > b , "Download ok");
        Assert.assertNotEquals(a,b);


    }

    @Test(priority = 2)
    public void validateFile() throws IOException {

        FileKan fk = new FileKan();
        String data = fk.readFile(fk.lastFileModi());
        String[] searchInto = data.split(",");
        List<String> compare = new ArrayList<String>();

        for(int i=0;i<searchInto.length;i++){
            //System.out.println("for"+searchInto[i]);
            if(searchInto[i].equals("415615") || searchInto[i].equals("Jhonatans country")){
                //System.out.println("if"+searchInto[i]);
                compare.add(searchInto[i]);
            }
        }

        if(compare.size()==2){
            Assert.assertTrue(compare.get(0).equals("415615"));
            Assert.assertTrue(compare.get(1).equals("Jhonatans country"));
        }

        Assert.assertTrue(compare.size()==2);

       /* if(compare.size()==2){

        //System.out.println(compare.get(0) + compare.get(1));
        if(compare.get(0).equals("415615") && compare.get(1).equals("Jhonatans country")){
            System.out.println("se encontró registro guardado");
        }else {
            System.out.println("NO se ha guardo nada");
        } }*/

        }

    }



