package com.course.app_medic.services.impl;

import org.springframework.stereotype.Service;

import com.course.app_medic.models.Exam;
import com.course.app_medic.repositories.IGenericRepo;
import com.course.app_medic.repositories.IExamRepo;
import com.course.app_medic.services.IExamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl extends CRUDImpl<Exam, Integer> implements IExamService {

    private final IExamRepo repo;

    @Override
    protected IGenericRepo<Exam, Integer> getRepo() {
        return repo;
    }

}