package org.enterprise.exam.frontend.controller;

import org.enterprise.exam.backend.entity.Dish;
import org.enterprise.exam.backend.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class DishController {

    private String formName;
    private String formDescription;

    @Autowired
    private DishService dishService;

    public List<Dish> getAllDishes(){
        return dishService.getAllDishes();
    }

    public void createDish(){
        dishService.createDish(formName, formDescription);
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormDescription() {
        return formDescription;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }
}
