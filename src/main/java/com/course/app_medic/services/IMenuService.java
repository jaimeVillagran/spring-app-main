package com.course.app_medic.services;

import java.util.List;

import com.course.app_medic.models.Menu;

// fachadas
public interface IMenuService extends ICRUD<Menu, Integer> {

    List<Menu> getMenusByUsername(String username);

}
