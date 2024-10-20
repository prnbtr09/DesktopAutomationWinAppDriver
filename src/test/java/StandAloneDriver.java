import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class StandAloneDriver {
    public static void main(String[] args) throws Exception {

        DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setCapability("app","C:\\Windows\\System32\\notepad.exe");
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("deviceName", "WindowsPC");

        WindowsDriver driver=new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
        driver.manage().window().maximize();
        driver.findElement(By.name("Settings")).click();
        driver.quit();
    }
}
