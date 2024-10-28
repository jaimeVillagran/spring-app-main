package com.course.app_medic.services.impl;

import org.springframework.stereotype.Service;

import com.course.app_medic.models.Specialty;
import com.course.app_medic.repositories.IGenericRepo;
import com.course.app_medic.repositories.ISpecialtyRepo;
import com.course.app_medic.services.ISpecialtyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl extends CRUDImpl<Specialty, Integer> implements ISpecialtyService {

    private final ISpecialtyRepo repo;

    @Override
    protected IGenericRepo<Specialty, Integer> getRepo() {
        return repo;
    }

}
