package org.enterprise.exam.frontend.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.List;

public class MenuPageObject extends PageObject {

    public MenuPageObject(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    @Override
    public boolean isOnPage() {
        return driver.getTitle().contains("Menu");
    }

    public boolean hasDishByName(String name){
        List<WebElement> elements = driver.findElements(
                By.xpath("//label[@class='dishName' and contains(text(),'"+name+"')]"));
        return ! elements.isEmpty();
    }

    public void selectDishByName(String name, boolean on){

        WebElement check = getCheckBoxForDish(name);

        if(on != check.isSelected()){
            check.click();
        }
    }

    private WebElement getCheckBoxForDish(String name){
        List<WebElement> elements = driver.findElements(
                By.xpath("//label[@class='dishName' and contains(text(),'"+name+"')]/parent::td/parent::tr//input"));

        return elements.get(0);
    }

    public boolean isDishSelected(String name){
        return getCheckBoxForDish(name).isSelected();
    }

    public HomePageObject create(LocalDate date){

        setText("createForm:date", date.toString());
        clickAndWait("createForm:createButton");

        HomePageObject home = new HomePageObject(driver, host, port);
        if(! home.isOnPage()){
            return null;
        }

        return home;
    }
}
