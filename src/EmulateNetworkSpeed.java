import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.network.Network;
import org.openqa.selenium.devtools.v102.network.model.ConnectionType;
import java.util.Optional;


public class EmulateNetworkSpeed {

	/*
	 * How to emulate network speeds with Selenium chromedevTools integration
	 * Emulate network speed and bring some latency
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String path = System.getProperty("user.dir") + "\\Driver\\chromedriver.exe";
		System.out.println(path);
		System.setProperty("webdriver.chrome.driver" , path);
		ChromeDriver driver = new ChromeDriver();  //we are explicitly using chromedriver only since webdriver don't expose chromeDevTools
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.emulateNetworkConditions(false, 3000, 20000, 10000, Optional.of(ConnectionType.ETHERNET)));
		
		long startTime = 
				System.currentTimeMillis();
		driver.get("http://localhost:4200/");
		driver.findElement(By.cssSelector("button[routerlink*='library']")).click();
		long endTime = System.currentTimeMillis();
		
		System.out.println("**** THE LATENCY IS"  + (endTime-startTime));
		driver.quit();
		

	}

}
