import java.net.URI;
import java.util.function.Predicate;

import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.*;;



public class BasicAuthentication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = System.getProperty("user.dir") + "\\Driver\\chromedriver.exe";
		System.out.println(path);
		System.setProperty("webdriver.chrome.driver" , path);
		ChromeDriver driver = new ChromeDriver();  //we are explicitly using chromedriver only since webdriver don't expose chromeDevTools
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		
	Predicate<URI> uriPredicate =uri -> uri.getHost().contains("httpbin.org");
	((HasAuthentication)driver).register(uriPredicate , UsernameAndPassword.of("foo", "bar"));
	driver.get("http://httpbin.org/basic-auth/foo/bar");
	driver.quit();
    
	
	}

}
