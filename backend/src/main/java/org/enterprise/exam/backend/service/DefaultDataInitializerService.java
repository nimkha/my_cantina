package org.enterprise.exam.backend.service;

import org.enterprise.exam.backend.entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private DishService dishService;

    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }

    private Long createDish(String name, String description){
        return attempt(() -> dishService.createDish(name, description));
    }

    @PostConstruct
    public void init(){
        ArrayList<Long> dishIds1 = new ArrayList<>();
        ArrayList<Long> dishIds2 = new ArrayList<>();

        dishIds1.add(createDish("pizza", "pepperoni"));
        dishIds1.add(createDish("chicken", "fried"));
        dishIds1.add(createDish("spaghetti", "carbonara"));
        dishIds2.add(createDish("burger", "triple"));
        dishIds2.add(createDish("name", "description"));

        attempt(() -> menuService.createMenu(LocalDate.now(), dishIds1));
        attempt(() -> menuService.createMenu(LocalDate.now(), dishIds2));
    }
}
