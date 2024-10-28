package com.course.app_medic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean // para desligarse de las operaciones del repositorio para una determinada entidad en el contexto del genérico
public interface IGenericRepo<T, ID> extends JpaRepository<T, ID> {

}