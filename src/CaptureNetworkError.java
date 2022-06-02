import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.network.Network;
import org.openqa.selenium.devtools.v102.network.model.ConnectionType;

public class CaptureNetworkError {

	/*
	 * This tes is when we want to capture the network request error if something goes wrong like connectivity failure 
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
		devTools.send(Network.emulateNetworkConditions(true, 3000, 20000, 10000, Optional.of(ConnectionType.ETHERNET)));
		devTools.addListener(Network.loadingFailed() , loadingFailed->
		{
			System.out.println(loadingFailed.getErrorText());
			System.out.println(loadingFailed.getTimestamp());
			
		});
		
		devTools.send(Network.emulateNetworkConditions(true, 3000, 20000, 100000, Optional.of(ConnectionType.ETHERNET)));
		long startTime = System.currentTimeMillis();
		driver.get("http://google.com");
		driver.findElement(By.name("q")).sendKeys("netflix" , Keys.ENTER);
		String title = driver.findElements(By.cssSelector(".LC20lb")).get(0).getText();
		System.out.println("***THE TITLE OF THE STORY IS " + " " + title );
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
		
		

	}

}
