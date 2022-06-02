import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.fetch.Fetch;
import org.openqa.selenium.devtools.v102.network.model.Request;

public class NetworkMocking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = System.getProperty("user.dir") + "\\Driver\\chromedriver.exe";
		System.out.println(path);
		System.setProperty("webdriver.chrome.driver" , path);
		ChromeDriver driver = new ChromeDriver();  //we are explicitly using chromedriver only since webdriver don't expose chromeDevTools
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		/*
		 * When we fire the event using Fetch.requestPaused , the request paused until the client responds with one of the 
		 * continued request  
		 */
		
		devTools.addListener(Fetch.requestPaused(), request -> 
		{
			Request req = request.getRequest();
			if(req.getUrl().contains("foe"))
			{
				String mockedURL = req.getUrl().replace("=foe", "=joe");	
				System.out.println("The new URL IS  "  + mockedURL);
				
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(mockedURL), Optional.of(request.getRequest().getMethod()), request.getRequest().getPostData(), Optional.empty(), Optional.empty()));
				
			}
			else
			{
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(request.getRequest().getUrl()), Optional.of(request.getRequest().getMethod()), request.getRequest().getPostData(), Optional.empty(), Optional.empty()));
							
			}
			
			driver.get("http://localhost:4200/");
			driver.findElement(By.cssSelector("button[routerlink*='library']")).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
		
		driver.quit();
		

	}

}
