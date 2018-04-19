package org.enterprise.exam.backend.service;

import org.enterprise.exam.backend.StubApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DishServiceTest extends ServiceTestBase{

    @Autowired
    private DishService dishService;

    @Test
    public void testCreateDish(){
        String name = "name";
        String description = "description";

        Long dishId = dishService.createDish(name, description);

        System.out.println("dish id: " + dishId);
        assertNotNull(dishId);
    }

    @Test
    public void testCreateTwoDishes(){
        String name = "name";
        String description = "description";

        Long dishId1 = dishService.createDish(name, description);
        Long dishId2 = dishService.createDish(name, description);

        assertNotNull(dishId1);
        assertNotNull(dishId2);
    }


}
