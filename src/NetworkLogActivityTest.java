import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.network.Network;
import org.openqa.selenium.devtools.v102.network.model.Request;
import org.openqa.selenium.devtools.v102.network.model.Response;

public class NetworkLogActivityTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		String path = System.getProperty("user.dir") + "\\Driver\\chromedriver.exe";
		System.out.println(path);
		System.setProperty("webdriver.chrome.driver" , path);
		ChromeDriver driver = new ChromeDriver();  //we are explicitly using chromedriver only since webdriver don't expose chromeDevTools
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
			
		/*
		 * EXTRACTING NETWORK API CALLS & STATUS CODES WITH SELENIUM CDP LISTENERS
		 */
		
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));  //will enable the network to listen by selenium
		
		devTools.addListener(Network.requestWillBeSent(), request ->
		{
			Request req = request.getRequest();
			System.out.println(req.getUrl());
		});
		
		//Event will get fired when u will get response back
		//devTools.addListener(event, handler);  selenium is listening when the event is fired and when the event is fired we will get response 
		devTools.addListener(Network.responseReceived(), response ->
		{
			Response res = response.getResponse();
			System.out.println("** THE RESPONSE  URL & STATUS CODE IS  " + res.getUrl() + " " + response.getResponse().getStatus());
			//System.out.println("** THE STATUS CODE IS  " + response.getResponse().getStatus());
			
			if(res.getStatus().toString().startsWith("4"))
			{
				System.out.println(res.getUrl() + "IS FAILING WITH STATUS CODE  " + res.getStatus());
			}
			
		}
				
				);
		
		driver.get("http://localhost:4200/");
		driver.findElement(By.cssSelector("button[routerlink*='library']")).click();
		Thread.sleep(5000);
		
		
		

	}

}
