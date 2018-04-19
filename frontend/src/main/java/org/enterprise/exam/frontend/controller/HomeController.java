package org.enterprise.exam.frontend.controller;

import org.enterprise.exam.backend.entity.Menu;
import org.enterprise.exam.backend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.time.LocalDate;

@Named
@RequestScoped
public class HomeController {

    private Menu current;

    @Autowired
    private MenuService menuService;

    public Menu getCurrentMenu(){
        if (current == null) goDefault();
        return current;
    }

    public Menu getNext(){
        Menu menu = getCurrentMenu();
        if (menu == null) return null;
        return menuService.getNext(menu.getDate());
    }

    public Menu getPrevious(){
        Menu menu = getCurrentMenu();
        if (menu == null) return null;
        return menuService.getPrevious(menu.getDate());
    }

    public Menu goDefault(){
        current = menuService.getCurrent();
        return current;
    }

    public void goNext(){
        current = getNext();
    }

    public void goPrevious(){
        current = getPrevious();
    }
}
