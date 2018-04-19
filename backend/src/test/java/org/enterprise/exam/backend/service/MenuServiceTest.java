package org.enterprise.exam.backend.service;

import org.enterprise.exam.backend.StubApplication;
import org.enterprise.exam.backend.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MenuServiceTest extends ServiceTestBase {

    @Autowired
    private MenuService menuService;

    @Autowired
    private DishService dishService;

    @Test
    public void testCreateMenuWithNoDish(){
        Long menuId = menuService.createMenu(LocalDate.now(), null);

        assertNotNull(menuId);
    }

    @Test
    public void testGetCurrentMenu(){
        Long dishId = dishService.createDish("foo", "bar");
        Long menuId = menuService.createMenu(LocalDate.now(), Arrays.asList(dishId));
        Menu menu = menuService.getCurrent();

        assertNotNull(menu);
    }

    @Test
    public void testGetAbsentPreviousMenu(){
        Long dishId = dishService.createDish("foo", "bar");
        Long menuId = menuService.createMenu(LocalDate.now(), Arrays.asList(dishId));
        Menu menu = menuService.getPrevious(LocalDate.now());

        assertNull(menu);
    }

    @Test
    public void testGetAbsentNextMenu(){
        Long dishId = dishService.createDish("foo", "bar");
        Long menuId = menuService.createMenu(LocalDate.now(), Arrays.asList(dishId));
        Menu menu = menuService.getNext(LocalDate.now());

        assertNull(menu);
    }

    @Test
    public void testGetPreviousMenu(){
        Long dishId = dishService.createDish("foo", "bar");
        menuService.createMenu(LocalDate.now(), Arrays.asList(dishId));
        menuService.createMenu(LocalDate.now().minusDays(1), Arrays.asList(dishId));

        Menu menu = menuService.getPrevious(LocalDate.now());

        assertNotNull(menu);
    }

    @Test
    public void testThreeMenus(){
        long dishId = dishService.createDish("foo", "bar");
        long today = menuService.createMenu(LocalDate.now(), Arrays.asList(dishId));
        long yesterday = menuService.createMenu(LocalDate.now().minusDays(1), Arrays.asList(dishId));
        long tomorrow = menuService.createMenu(LocalDate.now().plusDays(1), Arrays.asList(dishId));

        assertEquals(yesterday, (long) menuService.getPrevious(LocalDate.now()).getId());
        assertEquals(tomorrow, (long) menuService.getNext(LocalDate.now()).getId());

        assertNull(menuService.getNext(LocalDate.now().plusDays(1)));
        assertEquals(today, (long) menuService.getPrevious(LocalDate.now().plusDays(1)).getId());

        assertNull(menuService.getPrevious(LocalDate.now().minusDays(1)));
        assertEquals(today, (long) menuService.getNext(LocalDate.now().minusDays(1)).getId());
    }
}