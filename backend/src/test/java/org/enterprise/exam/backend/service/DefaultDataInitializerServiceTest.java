package org.enterprise.exam.backend.service;

import org.enterprise.exam.backend.StubApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = BEFORE_CLASS)
public class DefaultDataInitializerServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private DishService dishService;

    @Test
    public void testInit(){
        assertTrue(dishService.getAllDishes().size() > 0);
        System.out.println("number of dishes: " + dishService.getAllDishes().size());

        assertNotNull(menuService.getCurrent());
    }

}
