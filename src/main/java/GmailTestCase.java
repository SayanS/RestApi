
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class GmailTestCase {
    private WebDriver webDriver = new ChromeDriver();

    @Test
    public void checkGmailLogin() throws InterruptedException {
        webDriver.navigate().to("https://accounts.google.com");
        Thread.sleep(10000);
    }

}
