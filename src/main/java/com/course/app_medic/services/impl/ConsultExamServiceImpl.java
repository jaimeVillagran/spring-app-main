package com.course.app_medic.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.course.app_medic.models.ConsultExam;
import com.course.app_medic.repositories.IConsultExamRepo;
import com.course.app_medic.services.IConsultExamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultExamServiceImpl implements IConsultExamService {

    private final IConsultExamRepo repo;

    @Override
    public List<ConsultExam> getExamsByConsultId(Integer consultId) {
        return repo.getExamsByConsultId(consultId);
    }

}
