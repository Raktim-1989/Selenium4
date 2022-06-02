import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.network.Network;

import com.google.common.collect.ImmutableList;

public class BlockNetworkRequests {

	
	/*
	 * Block Network requests calls to speed up the Execution with Selenium
	 * Block the network request API calls so that it can't reach to the server like CSS loading and all 
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
		devTools.send(Network.setBlockedURLs(ImmutableList.of("*.jpg" , "*.css" , "*.jpeg")));
		long startTime = System.currentTimeMillis();
		driver.get("http://localhost:4200/");
		driver.findElement(By.linkText("Browse Products")).click();
		driver.findElement(By.linkText("Selenium")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		System.out.println(driver.findElement(By.cssSelector("p")).getText());
		long endTime = System.currentTimeMillis();
		System.out.println("*** THE TIME REQUIRED TO PERFORM ACTION IS  " + (endTime-startTime));
		

	}

}
