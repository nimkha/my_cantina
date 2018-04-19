package org.enterprise.exam.frontend.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DishesPageObject extends PageObject {

    public DishesPageObject(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    @Override
    public boolean isOnPage() {
        return driver.getTitle().contains("Dishes");
    }

    public void createDish(String name, String description){
        setText("createForm:formName", name);
        setText("createForm:formDescription", description);

        clickAndWait("createForm:createButton");
    }

    public boolean hasDishByName(String name){
        List<WebElement> elements = driver.findElements(
                By.xpath("//label[@class='dishName' and contains(text(),'"+name+"')]"));
        return ! elements.isEmpty();
    }
}
