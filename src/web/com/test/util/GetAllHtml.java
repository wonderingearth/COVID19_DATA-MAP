package web.com.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static web.com.test.util.FileIO.ChangeConfirmHtml.changeConfirmHtmlFile;
public class GetAllHtml {
    public static void main(String[] args) throws IOException {
        Arrays.addDates();
        for(int i = 20;i<Arrays.dates.size();i++){
            System.out.println("change to : "+Arrays.dates.get(i));
            changeConfirmHtmlFile(Arrays.dates.get(i));
        }
    }
}
