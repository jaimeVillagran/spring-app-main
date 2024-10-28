package com.course.app_medic.services;

import java.util.List;

import com.course.app_medic.models.ConsultExam;

public interface IConsultExamService {

    List<ConsultExam> getExamsByConsultId(Integer consultId);

}
