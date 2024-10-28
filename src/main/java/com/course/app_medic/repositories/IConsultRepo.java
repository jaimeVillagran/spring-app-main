package com.course.app_medic.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.course.app_medic.dtos.IConsultProcDTO;
import com.course.app_medic.models.Consult;

public interface IConsultRepo extends IGenericRepo<Consult, Integer> {

    // 1. una query de búsqueda de consultas mediante dni y fullname del paciente
    @Query("FROM Consult c WHERE c.patient.dni = :dni OR LOWER(c.patient.firstName) LIKE %:fullname% OR LOWER(c.patient.lastName) LIKE %:fullname%")
    List<Consult> search(@Param("dni") String dni, @Param("fullname") String fullname);

    // 2. lista de consultas mediante un rango de fechas
    // >= | <
    // 15-09-2023 al 30-09-2023
    @Query("FROM Consult c WHERE c.consultDate BETWEEN :date1 AND :date2")
    List<Consult> searchByDates(@Param("date1") LocalDateTime date1, @Param("date2") LocalDateTime date2);

    // 3. devolver la cantidad de consultas x fecha para un reporte mediante proc.
    // almacenado: nativeFunction -> array de objetos
    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<Object[]> callProcedureOrFunctionNative();

    // 4. devolver el detalle de consultas mediante procedimientos almacenados para
    // un reporte: projection
    @Query(value = "select * from fn_list()", nativeQuery = true)
    List<IConsultProcDTO> callProcedureOrFunctionProjection();
}
