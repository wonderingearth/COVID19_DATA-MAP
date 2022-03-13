package web.com.test.Spider;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import web.com.test.util.Arrays;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class GetExistingConfirmMapImage {
    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
        System.setProperty("webdriver.chrome.driver","" +
                "src\\main\\resources\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver browser = new ChromeDriver(chromeOptions);
        //全屏浏览器
        browser.manage().window().maximize();
        final String BASE_URL = "http://localhost:8888/ConfirmMap?date=";
        String temp = "";
        Arrays.addDates();
        int count = 0;

        for(int i = 24;i<Arrays.dates.size();i++){
            count++;
            if(Arrays.dates.get(i).equals("2022-03-12" +
                    ""))
                break;
            System.out.println("change to : "+Arrays.dates.get(i));
            {
                Thread.sleep(2000);
                browser.manage().deleteAllCookies();
                browser.get(BASE_URL + Arrays.dates.get(i));
                System.out.println(BASE_URL + Arrays.dates.get(i));
                Actions action = new Actions(browser);
                WebElement webElement = browser.findElement(By.cssSelector("body > div"));
                action.moveByOffset(100, 100);
                action.contextClick();
                action.perform();
                Thread.sleep(100);
                {
                    Robot robot = new Robot();
                    //键盘移动到“图像另存为”
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    Thread.sleep(1500);
                    //点击“图像另存为”
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    Thread.sleep(1500);
                    //重命名文件
                    String robotStr = ""+count;
                    for(int robotCount = 0;robotCount<robotStr.length();robotCount++){
                        switch(robotStr.charAt(robotCount)){
                            case '0':
                                robot.keyPress(KeyEvent.VK_0);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_0);
                                break;
                            case '1':
                                robot.keyPress(KeyEvent.VK_1);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_1);
                                break;
                            case '2':
                                robot.keyPress(KeyEvent.VK_2);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_2);
                                break;
                            case '3':
                                robot.keyPress(KeyEvent.VK_3);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_3);
                                break;
                            case '4':
                                robot.keyPress(KeyEvent.VK_4);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_4);
                                break;
                            case '5':
                                robot.keyPress(KeyEvent.VK_5);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_5);
                                break;
                            case '6':
                                robot.keyPress(KeyEvent.VK_6);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_6);
                                break;
                            case '7':
                                robot.keyPress(KeyEvent.VK_7);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_7);
                                break;
                            case '8':
                                robot.keyPress(KeyEvent.VK_8);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_8);
                                break;
                            case '9':
                                robot.keyPress(KeyEvent.VK_9);
                                Thread.sleep(100);
                                robot.keyRelease(KeyEvent.VK_9);
                                break;
                            default:



                        }
                    }
                    //点击“保存”
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    Thread.sleep(1000);
                }
                action.moveByOffset(-100, -100);
                action.perform();
            }
        }
    }
}
