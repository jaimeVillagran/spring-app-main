package com.course.app_medic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RolDTO {

    @EqualsAndHashCode.Include
    private Integer idRol;

    private String name;

    private String description;

}
