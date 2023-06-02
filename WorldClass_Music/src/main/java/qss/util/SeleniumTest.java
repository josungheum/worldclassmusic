package qss.util;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
 
public class SeleniumTest {
 
    public static void main(String[] args) {
 
        SeleniumTest selTest = new SeleniumTest();
        selTest.crawl();
        
    }
 
    
    //WebDriver
    private WebDriver driver;
    
    private WebElement webElement;
    
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\DEV/chromedriver74.exe";
    
    //크롤링 할 URL
    private String base_url;
    
    private int timer = 3;
    
    public SeleniumTest() {
        super();
 
        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        
                
        //Driver SetUp
         ChromeOptions options = new ChromeOptions();
         options.setCapability("ignoreProtectedModeSettings", true);
         driver = new ChromeDriver(options);
        
        base_url = "http://localhost:8080/qss";
        
        
        
    }
 
    public void crawl() {
 
        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
        	
        	//로그인 테스트 
        	driver.get(base_url);
        	
        	inputTest("id", "id", "gyuin777");
        	inputTest("id", "password", "1234");
 
            
 
            //로그인 버튼 클릭
        	btnClick("className", "btnLogin");
        	
            //대시보드 테스트
            btnClick("id", "ALL");
            btnClick("id", "CPU");
            btnClick("id", "RAM");
            btnClick("id", "HDD");
            btnClick("id", "PRINTER");
            btnClick("id", "CARD");
            btnClick("id", "BARCODE");
            btnClick("id", "NETWORK");
            selected("name", "statisticsTable_length", 4);
            selected("name", "mTable_length", 0);	
            inputTest("cssSelector", "input[type='search']", "서울");
            
            //브랜드 리스트 테스트
            mouseClick("id", "MAIN_MENU_STO00");
            aTagClick("id", "SUB_MENU_STO01");
            selected("name", "brandListTable_length", 4);
            inputTest("cssSelector", "input[type='search']", "서울");
            btnClick("id", "10000000001_20000000001");
            inputTest("name", "brandCode", "BR1234");
            btnClick("id", "saveBtn");
            btnClick("className", "btn-warning");
            btnClick("className", "btn-primary");
            
            
            
//            inputTest("name", "brandCode", "BR123");
            
           
            
            
            
//            Thread.sleep(30000);
//            
            
            Thread.sleep(1300000);
    
        } catch (Exception e) {
            
            e.printStackTrace();
        
        } finally {
 
            driver.close();
        }
 
    }
    
    private void mouseClick(String attr, String key) {
    	
    	
    	try {
    		Actions action = new Actions(driver);
        	if(attr.equals("id")) 
        		webElement = driver.findElement(By.id(key));
        	else if(attr.equals("name")) 
        		webElement = driver.findElement(By.name(key));
        	else if(attr.equals("className")) 
        		webElement = driver.findElement(By.className(key));
        	else if(attr.equals("cssSelector")) 
        		webElement = driver.findElement(By.cssSelector(key));
        	action.moveToElement(webElement).perform();
        	driver.manage().timeouts().implicitlyWait(timer,TimeUnit.SECONDS) ;
	    } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void aTagClick(String attr, String key) {
    	
        
    	try {
	    	if(attr.equals("id")) 
	    		webElement = driver.findElement(By.id(key));
	    	else if(attr.equals("name")) 
	    		webElement = driver.findElement(By.name(key));
	    	else if(attr.equals("className")) 
	    		webElement = driver.findElement(By.className(key));
	    	else if(attr.equals("cssSelector")) 
	    		webElement = driver.findElement(By.cssSelector(key));
	    	
	    	
	    	webElement.click();
	    	driver.manage().timeouts().implicitlyWait(timer,TimeUnit.SECONDS) ;
	    } catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void inputTest(String attr, String key, String value) {
    	try {
	    	if(attr.equals("id")) 
	    		webElement = driver.findElement(By.id(key));
	    	else if(attr.equals("name")) 
	    		webElement = driver.findElement(By.name(key));
	    	else if(attr.equals("className")) 
	    		webElement = driver.findElement(By.className(key));
	    	else if(attr.equals("cssSelector")) 
	    		webElement = driver.findElement(By.cssSelector(key));
	    	//value reset
	    	webElement.clear();
	    	//value set
	        webElement.sendKeys(value);
	      //query timer
	        driver.manage().timeouts().implicitlyWait(timer,TimeUnit.SECONDS) ;
	    } catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void selected(String attr, String key, int cnt) {
		try {
			Select select = null;
	    	
	    	if(attr.equals("id")) 
	    		select = new Select(driver.findElement(By.id(key)));
	    	else if(attr.equals("name")) 
	    		select = new Select(driver.findElement(By.name(key)));
	    	else if(attr.equals("className")) 
	    		select = new Select(driver.findElement(By.className(key)));
	    	
	    	for(int i = 0; i < cnt; i++) {
	    		select.selectByIndex(i);
	    	}
	    	//query timer
	    	driver.manage().timeouts().implicitlyWait(timer,TimeUnit.SECONDS) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        
		
	}

	@Autowired
	private void btnClick(String attr, String key) {
    	try {
			if(attr.equals("id")) 
				webElement = driver.findElement(By.id(key));
	    	else if(attr.equals("name")) 
	    		webElement = driver.findElement(By.name(key));
	    	else if(attr.equals("className")) 
	    		webElement = driver.findElement(By.className(key));
	    	else if(attr.equals("cssSelector")) 
	    		webElement = driver.findElement(By.cssSelector(key));
			//query timer
			driver.manage().timeouts().implicitlyWait(timer,TimeUnit.SECONDS) ;
			webElement.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
}
 


