import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DesktopAutomationAppiumRoot {
    public static void main(String[] args) throws Exception {

        Boolean rootCapabilities = true;
        runExe("C:\\Windows\\System32\\calc.exe");

        WindowsDriver rootWindowsDriver = StartWinAppDriver(rootCapabilities);

        WebElement newAppWindow = rootWindowsDriver.findElement(By.name("Calculator"));
        String nativeWindowHandle=newAppWindow.getAttribute("NativeWindowHandle");
        String hexwinHandle=Integer.toHexString(Integer.parseInt(nativeWindowHandle));

        DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
        desiredCapabilities.setCapability("ms:waitForAppLaunch",15);
        desiredCapabilities.setCapability("appTopLevelWindow",hexwinHandle);
        WindowsDriver calcWindowDriver=startAppiumService(desiredCapabilities);
        calcWindowDriver.findElement(By.name("Nine")).click();

        Thread.sleep(5000);
        killProcess("calc");
        calcWindowDriver.quit();
        stopAppiumDriver();



    }

    private static WindowsDriver StartWinAppDriver(Boolean root) throws IOException {


        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (root) {
            desiredCapabilities.setCapability("app", "Root");
        } else {
            desiredCapabilities.setCapability("app", "C:\\Windows\\System32\\calc.exe");
        }
        desiredCapabilities.setCapability("platformName", "Windows");
        desiredCapabilities.setCapability("deviceName", "WindowsPC");
        desiredCapabilities.setCapability("automationName", "windows");
        desiredCapabilities.setCapability("newCommandTimeOut", 120);
        desiredCapabilities.setCapability("unicodeKeyboard", true);


        return startAppiumService(desiredCapabilities);
    }

    private static WindowsDriver startAppiumService(DesiredCapabilities desiredCapabilities) throws IOException {

        try {
            String appiumService = "node";
            killProcess(appiumService);

            AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder().withCapabilities(desiredCapabilities).usingAnyFreePort().withStartUpTimeOut(5, TimeUnit.SECONDS);
            AppiumDriverLocalService appiumDriverLocalService = AppiumDriverLocalService.buildService(serviceBuilder);
            appiumDriverLocalService.start();
            return new WindowsDriver(new URL("http://localhost:" + appiumDriverLocalService.getUrl().getPort() + "/wd/hub"), desiredCapabilities);
        }
        catch (Exception ex){
            AppiumDriverLocalService appiumDriverLocalService=AppiumDriverLocalService.buildDefaultService();
            appiumDriverLocalService.start();
            return new WindowsDriver(appiumDriverLocalService,desiredCapabilities);
        }
        }


    private static void killProcess(String process) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("taskkill /F /IM " + process + ".exe");
    }

    private static void stopAppiumDriver() throws IOException {
        String appiumService = "node";
        killProcess(appiumService);
    }

    private static void runExe(String exeLocation) throws IOException {
        Runtime.getRuntime().exec(exeLocation);
    }
}