package com.course.app_medic.services.impl;

import java.lang.reflect.Method;
import java.util.List;

import com.course.app_medic.exception.ModelNotFoundException;
import com.course.app_medic.repositories.IGenericRepo;
import com.course.app_medic.services.ICRUD;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) throws Exception {
        // API JAVA REFLEXION
        Class<?> classType = t.getClass(); // obtiene la clase del tipo especifico
        String className = t.getClass().getSimpleName(); // el nombre de la clase
        String methodName = "setId" + className; // forma el tipo para establecer el id del elemento
        Method setIdMethod = classType.getMethod(methodName, id.getClass()); // obtiene el método con la referencia del
                                                                             // id
        setIdMethod.invoke(t, id); // invoca el método de reflexion con el tipo específico

        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND" + id));

        return getRepo().save(t); // actualiza el registro con los datos a partir de la verificación
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) {
        // Supplier: falta código
        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND" + id));
    }

    @Override
    public void delete(ID id) {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND" + id));
        getRepo().deleteById(id);
    }

}
