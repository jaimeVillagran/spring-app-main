package com.course.app_medic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.course.app_medic.models.Menu;

public interface IMenuRepo extends IGenericRepo<Menu, Integer> {

    // 1. una query para obtener menús x username
    @Query(value = """
            select m.* from menu_role mr
                           inner join user_role ur on ur.id_role = mr.id_role
                            inner join menu m on m.id_menu = mr.id_menu
                            inner join user_data u on u.id_user = ur.id_user
                            where u.username = :username""", nativeQuery = true)
    List<Menu> getMenusByUsername(@Param("username") String username);

}
