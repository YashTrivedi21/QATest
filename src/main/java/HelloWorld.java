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

// Since there is only one page to be tested (user module)
// all testcases are thus contained in main method itself
public class HelloWorld {

    // prints the list of elements names from the given list of web elements
    public static void printList(List<WebElement> divs){
        // set to store unique names
        HashSet<String> elementText = new HashSet<>();
        for(var i : divs){
            try{
                // adding only the text of current elements
                elementText.add(i.getText());
            }
            // ignore elements in between having no text value
            catch (org.openqa.selenium.NoSuchElementException ignored){}
        }
        // removing any extra digit associated with the string and printing it
        for(var i : elementText) System.out.println(i.replaceAll("\\d",""));
        System.out.println("\n\n\n");
    }
    public static void main(String[] args) throws InterruptedException {
        // setting up the driver
        System.setProperty("webdriver.edge.driver", "F:\\Downloads\\edgedriver_win64\\msedgedriver.exe\\");
        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        // go to the neverland website
        String neverland = "https://neverland.freshprints.com/dashboard/login";
        driver.get(neverland);

        // maximize the window
        // for mobile testing specific size value can be added
        driver.manage().window().maximize();

        // fill the details in the form
        WebElement emailField = driver.findElement(By.id("form-email"));
        String siteEmail = "interns2022@freshprints.com";
        emailField.sendKeys(siteEmail);
        WebElement passwordField = driver.findElement(By.id("form-password"));
        String sitePassword = "interns2022";
        passwordField.sendKeys(sitePassword);

        // press the submit button
        driver.findElement(By.xpath("/html/body/div[4]/div/div/form/div/div[5]/div/button")).click();

        // clicking on users button, avoiding the common error of element reference exception
        WebElement userButton = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/div/ul/li[3]/a"));
        try {
            userButton.click();
        } catch (org.openqa.selenium.StaleElementReferenceException ex){
            userButton.click();
        }

        // sorting name column by clicking on it
        // (similarly can be done for school, org and company field)
        WebElement nameColumn = driver.findElement(By.xpath("//*[@id=\"ngx-datatable-basic\"]/div/datatable-header/div/div[2]/datatable-header-cell[1]"));
        try{
            nameColumn.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e){
            nameColumn.click();
        }

        // printing all 8 pills from the top
        // pills include - all active, admin, client, printer etc
        List<WebElement> pills = driver.findElements(By.cssSelector("#tabs-wrapper div"));
        printList(pills);

        // pressing the create user button
        // using explicit wait due to visibility issues
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement createUserButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"sticky-search-filter\"]/div[2]/app-button/button")));
        createUserButton.click();

        // print all 6 types of users that can be crated
        // users include - admin, client, fulfilment center, Campus Manager etc
        List<WebElement> createUsers = driver.findElements(By.cssSelector("body > app-custom-overlay > div > div > div.user-creation-wizard.ng-tns-c140-3.ng-star-inserted > app-wizard-create-select > div > div"));
        printList(createUsers);

        // pressing the cross button to close the same window
        driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[1]")).click();

        // pressing order button
        WebElement orderButton = driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/div/a[2]/div"));
        orderButton.click();

        // print all three types of orders
        // orders include - regular order, group order and sample order
        List<WebElement> orders = driver.findElements(By.cssSelector("body > app-custom-overlay > div > div > app-create-order > div > div.create-order__orders.d-flex > a"));
        printList(orders);

        // closing the orders window using js executor
        WebElement closeButton = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", closeButton);

        // checking the input functionality
        WebElement inputBar = driver.findElement(By.xpath("//*[@id=\"sticky-search-filter\"]/app-search-filter/div[1]/div/div/label/input"));
        // string should have both cases and should not have perfect match but prefix match
        String inputText = "rAc cliEnt";
        inputBar.sendKeys(inputText);
        // using sleep for better visualisation
        Thread.sleep(2000);

        // pressing the create user button
        createUserButton.click();

        // pressing the admin button
        WebElement adminButton = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/app-wizard-create-select/div/div[1]"));
        adminButton.click();

        // getting the warnings for the input fields
        WebElement fullName = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[1]/div[2]/app-input/div/input"));
        String fullNameString = " ";
        WebElement phone = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[2]/div[2]/app-input/div/input"));
        String phoneNumberString = "";
        WebElement email = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[3]/div[2]/app-input/div/input"));
        String emailString = "";

        fullName.sendKeys(fullNameString);
        phone.sendKeys(phoneNumberString);
        email.sendKeys(emailString);

        // using sleep for better visibility
        Thread.sleep(2000);

        // printing the warnings
        String nameWarning = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[1]/div[2]/app-input/div/div/div")).getText();
        String phoneWarning = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-contact-info/div/div[2]/div[2]/app-input/div/div/div")).getText();
        System.out.println(nameWarning);
        System.out.println(phoneWarning);

        // filling valid data to create user
        fullNameString = "donald trump";
        phoneNumberString = "9494994458";
        emailString = "dt@gmail.com";
        fullName.sendKeys(fullNameString);
        phone.sendKeys(phoneNumberString);
        email.sendKeys(emailString);

        // pressing the set password button
        WebElement setPassword = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[1]/div/div[3]/app-button/button"));
        setPassword.click();

        // using sleep for better clarity of visualisation
        Thread.sleep(2000);

        // setting the passwords
        WebElement adminPassword = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[2]/div/div[2]/div/div[3]/app-set-password/div/div[1]/div[2]/app-input/div/input"));
        WebElement adminPasswordConfirm = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[2]/div/div[2]/div/div[3]/app-set-password/div/div[2]/div[2]/app-input/div/input"));
        String passwordString = "1234";
        adminPassword.sendKeys(passwordString);
        adminPasswordConfirm.sendKeys(passwordString);

        // clicking the submit button
        WebElement adminSubmit = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/div[2]/div[1]/app-admin/div/div/aw-wizard/div/aw-wizard-step[2]/div/div[2]/div/div[3]/app-button/button"));
        adminSubmit.click();

        // going back to home page with back to work button
        WebElement backToWorkButton = driver.findElement(By.xpath("/html/body/app-custom-overlay/div/div/app-edit-user/div/div[3]/div/app-button/button"));
        backToWorkButton.click();

        Thread.sleep(2000);
        driver.quit();
    }
}
