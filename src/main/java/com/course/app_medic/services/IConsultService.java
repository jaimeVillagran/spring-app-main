package com.course.app_medic.services;

import java.time.LocalDateTime;
import java.util.List;

import com.course.app_medic.dtos.ConsultProcDTO;
import com.course.app_medic.dtos.IConsultProcDTO;
import com.course.app_medic.models.Consult;
import com.course.app_medic.models.Exam;

// fachadas
public interface IConsultService extends ICRUD<Consult, Integer> {

    Consult saveTransactional(Consult consult, List<Exam> exams);

    List<Consult> search(String dni, String fullname);

    List<Consult> searchByDates(LocalDateTime date1, LocalDateTime date2);

    // objeto
    List<ConsultProcDTO> callProcedureOrFunctionNative();

    // interfaz
    List<IConsultProcDTO> callProcedureOrFunctionProjection();

    byte[] generateReport() throws Exception;

}