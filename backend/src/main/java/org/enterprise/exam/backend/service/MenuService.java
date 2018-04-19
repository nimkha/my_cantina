package org.enterprise.exam.backend.service;

import org.enterprise.exam.backend.entity.Dish;
import org.enterprise.exam.backend.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuService {

    @Autowired
    private EntityManager entityManager;

    public Long createMenu(@NotNull LocalDate date, List<Long> dishIds) {

        Menu menu;

        Query query = entityManager.createQuery("select m from Menu m where m.date = :date");
        query.setParameter("date", date);
        List list = query.getResultList();
        if(list.isEmpty()){
            menu = new Menu();
            menu.setDate(date);
        } else {
            menu = (Menu) list.get(0);
        }

        menu.getDishes().clear();

        if (dishIds != null) {
            for (Long id : dishIds) {
                Dish dish = entityManager.find(Dish.class, id);
                if (dish == null) {
                    throw new IllegalArgumentException("Invalid id for Dish");
                }
                menu.getDishes().add(dish);
            }
        }

        if(menu.getId() == null) {
            entityManager.persist(menu);
        }

        return menu.getId();
    }

    public Menu getCurrent() {
        TypedQuery<Menu> query = entityManager.createQuery("select m from Menu m where (m.date >= :current) order by m.date", Menu.class);
        query.setParameter("current", LocalDate.now());
        query.setMaxResults(1);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Menu) list.get(0);
        }
        return getPrevious(LocalDate.now());
    }

    public Menu getNext(@NotNull LocalDate current) {
        Query query = entityManager.createQuery("select m from Menu m where (m.date > :current) order by m.date ASC ");
        query.setParameter("current", current);
        query.setMaxResults(1);

        List list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return (Menu) list.get(0);
        }
    }

    public Menu getPrevious(@NotNull LocalDate current) {
        Query query = entityManager.createQuery("select m from Menu m where (m.date < :current) order by m.date DESC ");
        query.setParameter("current", current);
        query.setMaxResults(1);

        List list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return (Menu) list.get(0);
        }
    }

}
