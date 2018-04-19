package org.enterprise.exam.backend.service;

import org.enterprise.exam.backend.entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Transactional
public class DishService {

    @Autowired
    private EntityManager entityManager;

    public Long createDish(String name, String description){
        if (name == null || description == null) return null;

        Dish dish = new Dish();

        dish.setName(name);
        dish.setDescription(description);

        entityManager.persist(dish);

        return dish.getId();
    }

    public List<Dish> getAllDishes(){
        TypedQuery<Dish> getAll = entityManager.createQuery("select d from Dish d", Dish.class);

        return getAll.getResultList();
    }
}
