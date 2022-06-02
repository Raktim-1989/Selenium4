import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.fetch.Fetch;
import org.openqa.selenium.devtools.v102.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v102.network.model.ErrorReason;
import org.openqa.selenium.devtools.v102.network.model.Request;

public class NetworkFailRequest {
	
/*
 * Teesting Failed Network request calls using Selenium CDP commands 
 * 
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = System.getProperty("user.dir") + "\\Driver\\chromedriver.exe";
		System.out.println(path);
		System.setProperty("webdriver.chrome.driver" , path);
		ChromeDriver driver = new ChromeDriver();  //we are explicitly using chromedriver only since webdriver don't expose chromeDevTools
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		//java.util.Optional<java.lang.String> urlPattern
		Optional<List<RequestPattern>> patterns =  Optional.of(Arrays.asList(new RequestPattern(Optional.of("*GetBook*") , Optional.empty() , Optional.empty())));
		devTools.send(Fetch.enable(patterns, Optional.empty()));
        devTools.addListener(Fetch.requestPaused(), request -> 
        {
        	Request req = request.getRequest();
        	devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
        });       
        
        driver.get("http://localhost:4200/");
	
        driver.findElement(By.cssSelector("button[routerlink*='library']")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
