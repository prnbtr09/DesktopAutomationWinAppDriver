import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DesktopAutomationProcessBuilder {
    public static void main(String[] args) throws Exception {
        Process process = runWinAppDriverServer();
        try {
            Boolean runWithRootCapabilities = false;
            String desktopApplicationLocation = "C:\\Windows\\System32\\notepad.exe";
            WindowsDriver windowsDriver = StartWinAppDriver(runWithRootCapabilities, desktopApplicationLocation);
            windowsDriver.manage().window().maximize();
            windowsDriver.findElement(By.name("Settings")).click();

            //By Tag Name
            List<WebElement> groups = windowsDriver.findElementsByTagName("Group");
            System.out.println("Number of groups are " + groups.size());
            groups.forEach(a -> {
                System.out.println("Group text is " + a.getText());
            });


            //By Xpath:
            windowsDriver.findElement(By.xpath("//Group[@Name='Font']")).click();

            //By ClassName
            List<WebElement> comboBoxes = windowsDriver.findElements(By.className("ComboBox"));
            System.out.println("combo boxes on page size is " + comboBoxes.size());

            comboBoxes.forEach(a -> {
                System.out.println("Combo box text is " + a.getText());
            });


            //By Accessability Id
            windowsDriver.findElementByAccessibilityId("WordWrapSwitch").click();
            Thread.sleep(5000);
            windowsDriver.quit();

        } finally {
            stopWinAppDriverServer(process);
        }

    }

    public static Process runWinAppDriverServer() throws IOException {
        String winAppDriverPath = "C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe";
        ProcessBuilder builder = new ProcessBuilder(winAppDriverPath).inheritIO();
        return builder.start();

    }

    private static WindowsDriver StartWinAppDriver(Boolean root, String applicationLocation) throws IOException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        if (root) {
            desiredCapabilities.setCapability("app", "root");
        } else {
            desiredCapabilities.setCapability("app", applicationLocation);
        }
        desiredCapabilities.setCapability("platformName", "Windows");
        desiredCapabilities.setCapability("deviceName", "WindowsPC");
        return new WindowsDriver(new URL("http://127.0.0.1:4723"), desiredCapabilities);
    }

    public static void stopWinAppDriverServer(Process process) {
        process.destroy();
    }


}