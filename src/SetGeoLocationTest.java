import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

public class SetGeoLocationTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		String path = System.getProperty("user.dir") + "\\Driver\\chromedriver.exe";
		System.out.println(path);
		System.setProperty("webdriver.chrome.driver" , path);
		ChromeDriver driver = new ChromeDriver();  //we are explicitly using chromedriver only since webdriver don't expose chromeDevTools
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		//40 3 for madrid - spain - 
		Map<String , Object> mapCordinates = new HashMap<String , Object>();
		mapCordinates.put("latitude", 40);
		mapCordinates.put("longitude", 3);
		mapCordinates.put("accuracy", 1);
		driver.executeCdpCommand("Emulation.setGeolocationOverride", mapCordinates);
		driver.get("http://google.com");
		driver.findElement(By.name("q")).sendKeys("netflix" , Keys.ENTER);
		Thread.sleep(3000);
		String title = driver.findElements(By.cssSelector(".LC20lb")).get(0).getText();
		Thread.sleep(3000);
		System.out.println("***THE TITLE OF THE STORY IS " + " " + title );
		driver.quit();
		
		
	}

}
