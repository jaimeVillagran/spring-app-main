package com.course.app_medic.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ConsultExamPK.class)
public class ConsultExam {

    @Id
    private Consult consult;

    @Id
    private Exam exam;

    public ConsultExam(Exam exam) {
        this.exam = exam;
    }

    // FORMA 1 VS FORMA 2 (Simplificada)
    // + Código y archivos (Desventaja)
    // Mayor flexibilidad de comportamiento (Ventaja)

}
