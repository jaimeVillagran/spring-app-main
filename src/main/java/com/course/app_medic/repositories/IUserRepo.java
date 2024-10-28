package com.course.app_medic.repositories;

import com.course.app_medic.models.User;

public interface IUserRepo extends IGenericRepo<User, Integer> {

    // 1. obtener user by username: jpa query methods
    User findOneByUsername(String username);
}
