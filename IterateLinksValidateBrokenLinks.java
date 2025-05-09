import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class IterateLinksValidateBrokenLinks 
{
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws MalformedURLException, IOException
	{
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		
		List <WebElement> links = driver.findElements(By.cssSelector("li[class='gf-li'] a"));
		
		for (WebElement link : links) 
		{
			String url = link.getDomAttribute("href");
			
			if (url == null || url.isEmpty() || !url.startsWith("http")) 
			{
		        System.out.println("Skipping invalid or empty URL: " + url);
		        continue;
		    }
		
			HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
			conn.setRequestMethod("HEAD");
			conn.connect();
			int respCode = conn.getResponseCode();
			System.out.println(respCode);
			
			if (respCode > 400)
			{
				System.out.println("The link with text " + link.getText() + "is broken with code " + respCode);
				Assert.assertTrue(false);
				
			}
			
			
		}
	}
}
