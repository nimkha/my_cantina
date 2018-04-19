package org.enterprise.exam.frontend.controller;

import org.enterprise.exam.backend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class MenuController {

    private String dateForm;

    private Map<Long, Boolean> checksForm = new HashMap<>();

    @Autowired
    private MenuService menuService;

    public String createMenu() {

        try {
            LocalDate date = LocalDate.parse(dateForm);

            List<Long> ids = checksForm.entrySet().stream()
                    .filter(e -> e.getValue())
                    .map(e -> e.getKey())
                    .collect(Collectors.toList());

            menuService.createMenu(date, ids);

            return "/home.jsf";
        }catch (Exception e) {
            return "/menu.jsf";
        }
    }

    public String getDateForm() {
        return dateForm;
    }

    public void setDateForm(String dateForm) {
        this.dateForm = dateForm;
    }

    public Map<Long, Boolean> getChecksForm() {
        return checksForm;
    }

    public void setChecksForm(Map<Long, Boolean> checksForm) {
        this.checksForm = checksForm;
    }
}
