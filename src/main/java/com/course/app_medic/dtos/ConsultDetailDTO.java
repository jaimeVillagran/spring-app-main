package com.course.app_medic.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConsultDetailDTO {

    @EqualsAndHashCode.Include
    private Integer idDetail;

    @NotNull
    private String diagnosis;

    @NotNull
    private String treatment;

    @JsonBackReference
    private ConsultDTO consult;

}
