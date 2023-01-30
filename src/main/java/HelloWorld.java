import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;

public class HelloWorld {

    public static void printList(List<WebElement> divs){
        HashSet<String> pills = new HashSet<>();
        for(var i : divs){
            try{
                pills.add(i.getText());
            } catch (org.openqa.selenium.NoSuchElementException ignored){
            }
        }
        for(var i : pills) System.out.println(i.replaceAll("\\d",""));
        System.out.println("\n\n\n");
    }
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "F:\\Downloads\\edgedriver_win64\\msedgedriver.exe\\");
        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        // login to the neverland website
        driver.get("https://neverland.freshprints.com/dashboard/login");
        driver.manage().window().maximize();
        driver.findElement(By.id("form-email")).sendKeys("interns2022@freshprints.com");
        driver.findElement(By.id("form-password")).sendKeys("interns2022");
        driver.findElement(By.xpath("/html/body/div[4]/div/div/form/div/div[5]/div/button")).click();

        // clicking on users button
        try {
            driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/div/ul/li[3]/a")).click();
        } catch (org.openqa.selenium.StaleElementReferenceException ex){
            driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/div/ul/li[3]/a")).click();
        }

        // sorting name column by clicking on it
        try{
            driver.findElement(By.xpath("//*[@id=\"ngx-datatable-basic\"]/div/datatable-header/div/div[2]/datatable-header-cell[1]")).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e){
            driver.findElement(By.xpath("//*[@id=\"ngx-datatable-basic\"]/div/datatable-header/div/div[2]/datatable-header-cell[1]")).click();
        }

        // printing all 8 pills from the top
        List<WebElement> pills = driver.findElements(By.cssSelector("#tabs-wrapper div"));
        printList(pills);

        // pressing the create user button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement createUserButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"sticky-search-filter\"]/div[2]/app-button/button")));
        createUserButton.click();

        // print all 6 types of users that can be crated
        List<WebElement> create_users = driver.findElements(By.cssSelector("body > app-custom-overlay > div > div > div.user-creation-wizard.ng-tns-c140-3.ng-star-inserted > app-wizard-create-select > div > div"));
        printList(create_users);
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[1]")).click();

        // pressing order button
        driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/div/a[2]/div")).click();

        // print all three types of orders
        List<WebElement> orders = driver.findElements(By.cssSelector("body > app-custom-overlay > div > div > app-create-order > div > div.create-order__orders.d-flex > a"));
        printList(orders);
        WebElement element = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);

        // input functionality
        driver.findElement(By.xpath("//*[@id=\"sticky-search-filter\"]/app-search-filter/div[1]/div/div/label/input")).sendKeys("rac client");
        Thread.sleep(5000);

        // pressing the create user button
        createUserButton.click();
        // getting the warnings for the input fields
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/app-wizard-create-select/div/div[1]")).click();
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[1]/div[2]/app-input/div/input")).sendKeys(" ");
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[2]/div[2]/app-input/div/input")).sendKeys("");
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[3]/div[2]/app-input/div/input")).sendKeys("");
        Thread.sleep(2000);
        System.out.println(driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[1]/div[2]/app-input/div/div/div")).getText());
        System.out.println(driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[2]/div[2]/app-input/div/div/div")).getText());

        // filling valid data to create user
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[1]/div[2]/app-input/div/input")).sendKeys("donald trump");
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[2]/div[2]/app-input/div/input")).sendKeys("9494994458");
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[3]/div[2]/app-input/div/input")).sendKeys("dt@gmail.com");
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-button/button")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[2]/div/div[2]/div/div[3]/app-set-password/div/div[1]/div[2]/app-input/div/input")).sendKeys("1234");
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[2]/div/div[2]/div/div[3]/app-set-password/div/div[2]/div[2]/app-input/div/input")).sendKeys("1234");
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[2]/div/div[2]/div/div[3]/app-button/button")).click();
        Thread.sleep(2000);
        // going back to home page
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/app-edit-user/div/div[3]/div/app-button/button")).click();
        Thread.sleep(2000);
        driver.quit();
    }
}
