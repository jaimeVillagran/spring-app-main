package com.course.app_medic.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.course.app_medic.models.Patient;

// fachadas
public interface IPatientService extends ICRUD<Patient, Integer> {

    Page<Patient> listPage(Pageable pageable);

}
