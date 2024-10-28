package com.course.app_medic.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuDTO {

    @EqualsAndHashCode.Include
    private Integer idMenu;

    private String icon;
    private String name;
    private String url;
    private List<RolDTO> roles;

}
