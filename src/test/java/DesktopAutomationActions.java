import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DesktopAutomationActions {
    public static void main(String[] args) throws Exception {


        Boolean rootCapabilities = false;
        WindowsDriver windowsDriver = StartWinAppDriver(rootCapabilities);
        windowsDriver.manage().window().maximize();
       //By Name
        windowsDriver.findElement(By.name("Settings")).click();



        //By Xpath:
        windowsDriver.findElement(By.xpath("//Group[@Name='Font']")).click();

        //By ClassName
        List<WebElement> comboBoxes=windowsDriver.findElements(By.className("ComboBox"));


        System.out.println(windowsDriver.getWindowHandles().size()+" is size");

        WebElement element=windowsDriver.findElement(By.xpath("//ComboBox[@Name='Style']"));

        Actions actions=new Actions(windowsDriver);
        actions.click(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();




        Thread.sleep(5000);
        windowsDriver.quit();
        stopAppiumDriver();


    }

    private static WindowsDriver StartWinAppDriver(Boolean root) throws IOException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (root) {
            desiredCapabilities.setCapability("app", "root");
        } else {
            desiredCapabilities.setCapability("app", "C:\\Windows\\System32\\notepad.exe");
        }
        desiredCapabilities.setCapability("platformName", "Windows");
        desiredCapabilities.setCapability("deviceName", "WindowsPC");
        desiredCapabilities.setCapability("automationName","windows");
        desiredCapabilities.setCapability("newCommandTimeOut",120);
        desiredCapabilities.setCapability("unicodeKeyboard",true);


        return startAppiumService(desiredCapabilities);
    }

    private static WindowsDriver startAppiumService(DesiredCapabilities desiredCapabilities) throws IOException {
        String appiumService = "node";
        killProcess(appiumService);

        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder().withCapabilities(desiredCapabilities).usingAnyFreePort().withStartUpTimeOut(5, TimeUnit.SECONDS);
        AppiumDriverLocalService appiumDriverLocalService = AppiumDriverLocalService.buildService(serviceBuilder);
        appiumDriverLocalService.start();
        return new WindowsDriver(new URL("http://localhost:" + appiumDriverLocalService.getUrl().getPort() + "/wd/hub"), desiredCapabilities);
    }


    private static void killProcess(String process) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("taskkill /F /IM " + process + ".exe");
    }

    private static void stopAppiumDriver() throws IOException {
        String appiumService = "node";
        killProcess(appiumService);
    }


}