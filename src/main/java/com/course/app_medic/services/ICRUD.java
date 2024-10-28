package com.course.app_medic.services;

import java.util.List;

public interface ICRUD<T, ID> {

    T save(T t);

    T update(T t, ID id) throws Exception;

    List<T> findAll();

    T findById(ID id);

    void delete(ID id);

}
