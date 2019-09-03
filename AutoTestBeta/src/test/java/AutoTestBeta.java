import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AutoTestBeta {
    static WebDriver browser;
    static String newIssueSummary = "Attachment file selenium test " + new Date();
    static String newIssueURL;
    static String attachmentPath = "F:/EclipseWorkspace/AutoTests/selenium.jpg";
    static String attachmentName = attachmentPath.substring(attachmentPath.lastIndexOf("/") + 1);
    static String uploadPath = "C:/Users/User/Downloads";
    //helpers
    public static WebElement findElement(String cssSelector) {
        return browser.findElement(By.cssSelector(cssSelector));
    }
    public static void findAndClick(String cssSelector) {
        browser.findElement(By.cssSelector(cssSelector)).click();
    }
    public static void findAndWrite(String cssSelector, String text) {
        WebElement element = browser.findElement(By.cssSelector(cssSelector));
        element.clear();
        element.sendKeys(text);
    }
    public static boolean elementExists(String cssSelector) {
        return browser.findElements(By.cssSelector(cssSelector)).size() != 0;
    }
//    // downloading attachment to dir
//    public static boolean isFileDownloaded(String fileName) {
//        boolean flag = false;
//        File dir = new File(uploadPath);
//        File[] dir_contents = dir.listFiles();
//
//        for (int i = 0; i < dir_contents.length; i++) {
//            if (dir_contents[i].getName().equals(fileName))
//                return flag = true;
//        }
//        return flag;
//    }
//    // getting md5
//    public static String getMD5 (String filePath) throws NoSuchAlgorithmException, IOException {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(Files.readAllBytes(Paths.get(filePath)));
//        byte[] digest = md.digest();
//        return Arrays.toString(digest);
//    }
    @BeforeTest
    public static void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        browser.get("http://my.skyfencenet.com");
    }
//    @AfterTest
//
//    public static void closeBrowser() {
//        browser.quit();
//    }

    @Test ()
    public static void BetaLogin () {
        findAndWrite("input[id='userName']", "andrii@qabeta2.com");
        findAndWrite("input[id='userPassword']", "Barbapapa1@");
        findAndClick("input[id='submitBtn']");
        Assert.assertTrue(elementExists("div[class='global-page-img global-page__USER__img']"));
    }
    @Test (dependsOnMethods = {"BetaLogin"})
    public static void AssetCreation () throws InterruptedException {
        findAndClick("div[class='global-page-img global-page__SETTINGS__img']");
        browser.switchTo().frame(0);
        WebDriverWait wait = new WebDriverWait(browser, 5);
        wait.until(ExpectedConditions.elementToBeClickable(
                findElement("div[class='add_button']"))).click();
        findAndWrite("input[id='search_field']", "Box");
        findAndClick("div[id='asset_type_box_BOX']");
        findAndClick("div[id='next_btn']");
        findAndWrite("input[id='assetNameInput']", "Box for auto tests");
        findAndClick("div[id='next_btn']");
        Thread.sleep(3000);
    }

    @Test (dependsOnMethods = {"AssetCreation"})
    public static void APIConnectionIsFunctional () {
        ((JavascriptExecutor) browser).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        browser.switchTo().frame(0);
        findAndClick("div[id='asset_entitlements_section_']");
        ((JavascriptExecutor) browser).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebDriverWait wait = new WebDriverWait(browser, 5);
        wait.until(ExpectedConditions.elementToBeClickable(
                findElement("div[id='offlineGetCredentialsBtnAUTH2_button']"))).click();
//        findAndClick("div[id='offlineGetCredentialsBtnAUTH2_button']");
        findAndClick("div[id='confirmation_save_btn']");

        String winHandleBefore = browser.getWindowHandle();
        for(String winHandle : browser.getWindowHandles()) {
            browser.switchTo().window(winHandle);
        }
        findAndWrite("input[id='login']","user1@skyromi.onmicrosoft.com");
        findAndWrite("input[id='login']","Barbapapa1@3");
        findAndClick("input[name='login_submit']");

        browser.close();

        browser.switchTo().window(winHandleBefore);


    }
//        findAndClick("div[id='confirmation_save_btn']");
//        findAndWrite("input[class='text long-field']", newIssueSummary);
//        findAndWrite("input[id='assignee-field']", "Robert");
//        findAndClick("input[id='create-issue-submit']");
//        findElement("a.issue-created-key");
//        Assert.assertTrue(elementExists("a.issue-created-key"));
//        newIssueURL = findElement("a.issue-created-key").getAttribute("href");
    }
//    @Test(dependsOnMethods = {"createIssue"})
//    public static void viewIssue () {
//        browser.get(newIssueURL);
//        Assert.assertEquals(newIssueSummary, findElement("h1#summary-val").getText());
//    }
//    @Test(dependsOnMethods = {"createIssue"})
//    public static void createIssueAttachment() {
//        browser.get(newIssueURL);
//        findElement("input[class='issue-drop-zone__file ignore-inline-attach']").sendKeys(attachmentPath);
//        findElement("span[style='width: 100%;']");
//        browser.get(newIssueURL);
//        Assert.assertEquals(attachmentName, findElement("a[class='attachment-title']").getText());
//    }
//    @Test (dependsOnMethods = {"createIssueAttachment"})
//    public static void downloadIssueAttachment() throws InterruptedException, IOException, NoSuchAlgorithmException {
//        browser.get(newIssueURL);
//        findAndClick("div[class='attachment-thumb']");
//        Thread.sleep(5000);
//        findAndClick("a[id='cp-control-panel-download']");
//        Thread.sleep(5000);
//        Assert.assertTrue(isFileDownloaded(attachmentName),"File names don't match of file downloading failed!");
//        Assert.assertTrue(Objects.equals(getMD5(uploadPath + "/" + attachmentName), getMD5(attachmentPath)),"MD5 is incorrect!");
//        new File(uploadPath + "/" + attachmentName).delete();
//    }
//}