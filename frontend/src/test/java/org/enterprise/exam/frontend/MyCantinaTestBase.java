package org.enterprise.exam.frontend;

import org.enterprise.exam.backend.entity.Dish;
import org.enterprise.exam.frontend.page_object.DishesPageObject;
import org.enterprise.exam.frontend.page_object.HomePageObject;
import org.enterprise.exam.frontend.page_object.MenuPageObject;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class MyCantinaTestBase {

    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    private HomePageObject home;

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId(){
        return "foo_myCantinaIT_" + counter.getAndIncrement();
    }

    @Before
    public void initTest() {
        getDriver().manage().deleteAllCookies();

        home = new HomePageObject(getDriver(), getServerHost(), getServerPort());

        home.toStartingPage();

        assertTrue(home.isOnPage());
    }

    @Test
    public void testHomePage(){
        assertTrue(home.isOnPage());
    }

    @Test
    public void testCreateDish(){
        DishesPageObject dish = home.toDishes();
        assertTrue(dish.isOnPage());

        String name = getUniqueId();
        String description = "unique description";

        assertFalse(dish.hasDishByName(name));

        dish.createDish(name, description);

        assertTrue(dish.hasDishByName(name));
    }

    @Test
    public void testMenu(){
        DishesPageObject dish = home.toDishes();
        assertTrue(dish.isOnPage());

        String description = "unique description";
        String nameOne = getUniqueId();
        String nameTwo = getUniqueId();
        String nameThree = getUniqueId();

        assertFalse(dish.hasDishByName(nameOne));
        assertFalse(dish.hasDishByName(nameTwo));
        assertFalse(dish.hasDishByName(nameThree));

        dish.createDish(nameOne, description);
        dish.createDish(nameTwo, description);
        dish.createDish(nameThree, description);

        home = dish.toHomePage();

        MenuPageObject menu = home.toMenu();

        assertTrue(menu.isOnPage());
        assertTrue(menu.hasDishByName(nameOne));
        assertTrue(menu.hasDishByName(nameTwo));
        assertTrue(menu.hasDishByName(nameThree));

        menu.selectDishByName(nameOne, true);
        menu.selectDishByName(nameTwo, true);
        LocalDate now = LocalDate.now();
        home = menu.create(now);

        home.displayDefault();

        assertEquals(now, home.getDisplayedDate());

        List<String> dishes = home.getDisplayedDishNames();

        assertTrue(dishes.contains(nameOne));
        assertTrue(dishes.contains(nameTwo));
        assertEquals(2, dishes.size());
    }

    @Test
    public void testDifferentDates(){
        MenuPageObject menu = home.toMenu();

        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        home = menu.create(yesterday);
        home.toMenu();
        home = menu.create(tomorrow);
        assertTrue(home.isOnPage());

        home.displayDefault();
        assertEquals(today ,home.getDisplayedDate());

        home.clickNext();
        assertEquals(tomorrow, home.getDisplayedDate());

        home.clickPrevious();
        assertEquals(yesterday, home.getDisplayedDate());
    }

}






















