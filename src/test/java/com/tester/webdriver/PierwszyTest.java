package com.tester.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class PierwszyTest {

    WebDriver driver;

   @BeforeTest
   public void setUp() {
       System.setProperty("webdriver.chrome.driver", "c:\\pliki\\chromedriver.exe");
//       System.setProperty("webdriver.gecko.driver", "c:\\pliki\\geckodriver.exe");
//       System.setProperty("webdriver.edge.driver", "c:\\pliki\\MicrosoftWebdriver.exe");
//       System.setProperty("webdriver.ie.driver", "c:\\pliki\\IEDriverServer.exe");

       driver=new ChromeDriver();
//       driver=new FirefoxDriver();
//       driver=new EdgeDriver();
//       driver=new InternetExplorerDriver();
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

   }

   @AfterTest
   public void tearDown() {
       driver.quit();
   }

    @Test
    public void startWebdriver(){
        driver.navigate().to("http://192.168.1.9");
        Assert.assertTrue(driver.getTitle().startsWith("Selenium kurs"), "strona startuje z innym tytulem");
    }

    @Test
    public void innyTest() {
        driver.get("http://helion.pl");
        String tytul = driver.getTitle();
        Assert.assertTrue(tytul.contains("Helion"),"testujemy tytul strony");
    }

    @Test //metoda będąca testem
    public void testDucha() {
        driver.get("http://192.168.1.9");
//        WebElement logo = driver.findElement(By.cssSelector("img#myImage"));
        WebElement logo = driver.findElement(By.id("myImage"));
        Assert.assertTrue(logo.isDisplayed());
    }

    @Test //metoda będąca testem
    public void helionLogo() {
        driver.get("http://helion.pl");
        WebElement logo = driver.findElement(By.cssSelector("p.logo"));
        Assert.assertTrue(logo.isDisplayed());
    }

    @Test //metoda będąca testem - test dostepnosci ksiazki
    public void helionSelenium() {
        driver.get("http://helion.pl");
        //WebElement search = driver.findElement(By.id("inputSearch"));
        WebElement search = driver.findElement(By.cssSelector("input#inputSearch"));
        search.sendKeys("selenium");
        WebElement searchButton = driver.findElement(By.cssSelector(".button"));
        searchButton.click();
        List<WebElement> wyniki = driver.findElements(By.partialLinkText("Selenium"));
        System.out.println("ilosc ksiazek " + wyniki.size());
        Assert.assertTrue(wyniki.size() > 0);
        wyniki.get(1).click();
        List<WebElement> tytul = driver.findElements(By.cssSelector(".title-group"));
        String tytulKsiazki = tytul.get(0).getText();
        Assert.assertTrue(tytulKsiazki.contains("Selenium"));
        System.out.println("znaleziony tytul: " + tytulKsiazki);
        try {
            Assert.assertTrue(tytulKsiazki.contains("Selenium"));
            System.out.println("tytul ksiazki to: " + tytulKsiazki);
        } catch (Throwable e) {
            System.out.println("UWAGA: oj cos jest nie tak");
        }
        WebElement okladka = driver.findElement(By.className("cover"));
        okladka.click();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //FileUtils.copyFile(screenshot, new File("c:\\test\\okladka1.png"));
    }
    @Test //metoda będąca testem
    public void testTextu() throws InterruptedException {
        driver.get("http://192.168.1.9");
        WebElement link = driver.findElement(By.partialLinkText("podstawowe"));
        link.click();
        WebElement imie = driver.findElement(By.id("Name"));
        imie.sendKeys("Ala");
        WebElement nazwisko = driver.findElement(By.id("Surname"));
        nazwisko.sendKeys("Kowalska");
        WebElement komentarz = driver.findElement(By.name("Komentarz"));
        komentarz.clear();
        komentarz.sendKeys("W tym formularzu to - to pole wymaga komentarza");
        String sprawdzamImie = imie.getAttribute("value");
        System.out.println("podane imie to: " + sprawdzamImie);
        Assert.assertTrue(sprawdzamImie.equals("Ala"));
        String sprawdzamNazwisko = nazwisko.getAttribute("value");
        System.out.println("podane nazwisko to: " + sprawdzamNazwisko);
        Assert.assertTrue(sprawdzamNazwisko.equals("Kowalska"));
        String sprawdzamKomentarz = komentarz.getAttribute("value");
        System.out.println("podany komentarz to: " + sprawdzamKomentarz);
        Assert.assertTrue(sprawdzamKomentarz.contains("to - to"));
    }

    @Test //metoda będąca testem
    public void testRadio() throws InterruptedException {
        driver.get("http://192.168.1.9");
        WebElement  link = driver.findElement(By.partialLinkText("podstawowe"));
        link.click();
        //wybieramy a nastepnie zaznaczamy przez klikniecie radio button Kobieta
        WebElement kobieta = driver.findElement(By.cssSelector("input[value='Kobieta']"));
        kobieta.click();
        Thread.sleep(10000);
        //pobieramy liste elementow typu plec (wszystkie radiobuttony)
        List<WebElement> plec = driver.findElements(By.cssSelector("input[name='Plec']"));
        // sprawdzamy jak duza jest tablica plec
        System.out.println("liczba radio buttonow - plec= " + plec.size());
        for (int i = 0; i < plec.size(); i++) {
            //szukamy elementu ktory jest zaznaczony
            if (plec.get(i).isSelected()) {  //sprawdzamy co jest zaznaczon
                String jakaPlec = plec.get(i).getAttribute("value");
                System.out.println("zaznaczony jest element: " + i
                        + " o wartosci: " + jakaPlec);
                Assert.assertTrue(jakaPlec.equals("Kobieta"));
            } else {  //wypisujemy co nie jest zaznaczone
                String jakaPlec = plec.get(i).getAttribute("value");
                System.out.println("ten element nie jest zaznaczony: " + i
                        + " o wartosci: " + jakaPlec);
                Assert.assertFalse(jakaPlec.equals("Kobieta"));
            }
        }
    }
    @Test //metoda będąca testem
    public void testMuzy() throws InterruptedException {
        driver.get("http://192.168.1.9");
        WebElement  link = driver.findElement(By.partialLinkText("podstawowe"));
        link.click();
        //wybieramy a nastepnie zaznaczamy przez klikniecie check boxy pop i classic
        WebElement pop = driver.findElement(By.cssSelector("input[value='Pop']"));
        pop.click();
        WebElement classic = driver.findElement(By.cssSelector("input[value='Muzyka powazna']"));
        classic.click();
        Thread.sleep(10000);
        //pobieramy liste elementow typu Muzyka (wszystkie check boxy)
        List<WebElement> muzyka = driver.findElements(By.cssSelector("input[name='Muzyka']"));
        // sprawdzamy jak duza jest tablica muzyka
        System.out.println("liczba check boxow - Muzyka= " + muzyka.size());
        for (int i = 0; i < muzyka.size(); i++) {
            //szukamy elementu ktory jest zaznaczony
            if (muzyka.get(i).isSelected()) {  //sprawdzamy co jest zaznaczone
                String jakaMuza = muzyka.get(i).getAttribute("value");
                System.out.println("zaznaczony jest element: " + i
                        + " o wartosci: " + jakaMuza);
                Assert.assertNotEquals(jakaMuza.equals("Pop"), jakaMuza.equals("Muzyka powazna"));
            } else {//wypisujemy co nie jest zaznaczone
                String jakaMuza = muzyka.get(i).getAttribute("value");
                System.out.println("ten element nie jest zaznaczony: " + i
                        + " o wartosci: " + jakaMuza);
                Assert.assertEquals(jakaMuza.equals("Pop"), jakaMuza.equals("Muzyka powazna"));
            }
        }

    }

    @Test //metoda będąca testem
    public void testAlert() throws InterruptedException {
        driver.get("http://192.168.1.9");
        WebElement link1 = driver.findElement(By.partialLinkText("dodatkowe"));
        link1.click();
        WebElement link2 = driver.findElement(By.partialLinkText("alerty"));
        link2.click();
        WebElement alert = driver.findElement(By.id("btnAlert"));
        alert.click();

        String alertMessage = driver.switchTo().alert().getText();

        driver.switchTo().alert().accept();

        System.out.println(alertMessage);
        Assert.assertTrue(alertMessage.contains("blokowany"));
    }

    @DataProvider
    public Object[][] dataForSearchTest() {
        return new Object[][] {
                {"Selenium", 3}, {"Java", 21}, {"Kali", 4},
                {"Jenkins", 3}, {"JavaScript", 20}, {"Git", 10}
        };
    }

    @Test (dataProvider = "dataForSearchTest")
    public void helionKsiazkiData(String tytul, int ilosc) throws InterruptedException {
        System.out.println("-------------- Rozpoczynamy nowy test ------------------- ");
        System.out.println("test dla tytulu: " + tytul + " oraz zalozonej ilosci: " + ilosc);
        driver.get("http://helion.pl");
        WebElement search = driver.findElement(By.cssSelector("input#inputSearch"));
        search.sendKeys(tytul);
        WebElement searchButton = driver.findElement(By.cssSelector(".button"));
        searchButton.click();
        Thread.sleep(10000);
        List<WebElement> wyniki = driver.findElements(By.partialLinkText(tytul));
        System.out.println("ilosc ksiazek: " + wyniki.size());
        Assert.assertTrue(wyniki.size() == ilosc);
    }



}
