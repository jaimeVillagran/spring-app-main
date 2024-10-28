package com.course.app_medic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.course.app_medic.models.ConsultExam;

public interface IConsultExamRepo extends IGenericRepo<ConsultExam, Integer> {

    // 1. inserción de consultas mediante ids con sus id examenes
    @Modifying
    @Query(value = "INSERT INTO consult_exam(id_consult, id_exam) VALUES (:idConsult, :idExam)", nativeQuery = true)
    Integer saveExam(@Param("idConsult") Integer idConsult, @Param("idExam") Integer idExam);

    // 2. obtener examenes mediante un id de consulta
    @Query("SELECT new ConsultExam(ce.exam) FROM ConsultExam ce WHERE ce.consult.idConsult = :idConsult")
    // @Query("SELECT new com.course.app_medic.models.ConsultExam(ce.exam) FROM
    // ConsultExam ce WHERE ce.consult.idConsult = :idConsult")
    List<ConsultExam> getExamsByConsultId(@Param("idConsult") Integer idConsult);

}
