package com.course.app_medic.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Menu {

    @Id
    @EqualsAndHashCode.Include
    private Integer idMenu;

    @Column(length = 20, nullable = false)
    private String icon;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "menu_role", joinColumns = @JoinColumn(name = "id_menu", referencedColumnName = "idMenu"), inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "idRole"))
    private List<Role> roles;

}
