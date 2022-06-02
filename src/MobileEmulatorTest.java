import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.emulation.Emulation;


public class MobileEmulatorTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//WebDriverManager.chromedriver().setup();
		String path = System.getProperty("user.dir") + "\\Driver\\chromedriver.exe";
		System.out.println(path);
		System.setProperty("webdriver.chrome.driver" , path);
		ChromeDriver driver = new ChromeDriver();  //we are explicitly using chromedriver only since webdriver don't expose chromeDevTools
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		//send commands to invoke CDP methods ==> Then CDP methods invoked and get access to ChromeDevTools 
		//for this case Emulation.setDeviceMetricsOverride # method will be invoked 
		devTools.send(Emulation.setDeviceMetricsOverride(600, 1000, 50, true, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        driver.get("http://localhost:4200/");	
        Thread.sleep(5000);
        boolean flag = driver.findElement(By.xpath("//button[@class='navbar-toggler']")).isDisplayed();
        System.out.println("THE BUTTON IS DISPLAYED  *****"  + flag );
        

	}

}
