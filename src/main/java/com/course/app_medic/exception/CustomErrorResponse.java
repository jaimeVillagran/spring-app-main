package com.course.app_medic.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Unicidad de formato de las excepciones
// Creas una clase POJO: clase básica con métodos getters y setters
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {

    private LocalDateTime datetime;
    private String message;
    private String details;    
}
