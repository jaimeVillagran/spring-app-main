package com.course.app_medic.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

// Clase: Va a ser para definir cuales son las credenciales mediante las cuales me voy a autenticar
@Data
@AllArgsConstructor
public class JwtRequest implements Serializable {

    private String username;
    private String password;

}
