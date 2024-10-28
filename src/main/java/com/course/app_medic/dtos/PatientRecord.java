package com.course.app_medic.dtos;

// se encuentran disponibles desde la java v15 en adelante
// crean objetos inmutables con sus getter pero sin setters
public record PatientRecord(
        Integer idPatient,
        String primaryName,
        String surname,
        String dni,
        String address,
        String phone,
        String email) {

}
