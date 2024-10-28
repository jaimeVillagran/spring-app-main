package com.course.app_medic.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConsultDTO {

    @EqualsAndHashCode.Include
    private Integer idConsult;

    @NotNull
    private PatientDTO patient;

    @NotNull
    private MedicDTO medic;

    @NotNull
    private SpecialtyDTO specialty;

    @NotNull
    private LocalDateTime consultDate;

    @NotNull
    private String numConsult;

    @JsonManagedReference
    @NotNull
    private List<ConsultDetailDTO> details;

}
