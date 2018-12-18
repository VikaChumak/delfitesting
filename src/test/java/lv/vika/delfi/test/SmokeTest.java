package lv.vika.delfi.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SmokeTest {

    private static final String PAGE_URL="https://www.delfi.lv/eda/recepty/salat-iz-brokkoli-s-yablokami?id=49990813";
    @Test
    public void doSomethingTest() {

        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(PAGE_URL);
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);

        WebElement title = driver.findElement(By.xpath("//h1[@class='article-title']"));
        String titlevalue = title.getText();
        List<WebElement> ingridientList = driver.findElements(By.xpath("//div[contains(@class, 'ingredients elevated')]/div[3]/div/a"));
        for (int i = 1; i < ingridientList.size() + 1; i++) {
            WebElement ingridient = driver.findElement(By.xpath("//div[contains(@class,'ingredients elevated')]//div[" + i + "]/a"));
            ingridient.click();
            List<WebElement> pages = driver.findElements(By.xpath("//a[contains(@class,'hl3-paging-number')]"));
            if (pages.size() == 0) {
                WebElement receipe = driver.findElement(By.xpath("//h1[text()='" + titlevalue + "']"));
                Assertions.assertTrue(receipe.isDisplayed());
                driver.navigate().back();
            }else {
                String pageUrl = driver.getCurrentUrl();
                boolean titleIsFound=false;
                for (int k=1;k<pages.size()+1;k++){
                    driver.get(pageUrl+"?page="+k);
                    List<WebElement> receipeLst=driver.findElements(By.xpath("//h1[text()='" + titlevalue + "']"));
                    if (receipeLst.size()>0){
                        titleIsFound=true;
                        break;
                    }
                }
                Assertions.assertTrue(titleIsFound);
                driver.get(PAGE_URL);
            }
        }
        driver.quit();
    }
}
