package org.enterprise.exam.backend.service;

import org.enterprise.exam.backend.entity.Dish;
import org.enterprise.exam.backend.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
@Transactional
public class ResetService {

    @Autowired
    private EntityManager entityManager;

    public void resetDatabase(){
        deleteEntities(Menu.class);
        deleteEntities(Dish.class);
    }

    private void deleteEntities(Class<?> entity){

        if(entity == null || entity.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException("Invalid non-entity class");
        }

        String name = entity.getSimpleName();

        Query query = entityManager.createQuery("delete from " + name);
        query.executeUpdate();
    }

}
