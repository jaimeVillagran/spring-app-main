package com.course.app_medic.services.impl;

import org.springframework.stereotype.Service;

import com.course.app_medic.models.Medic;
import com.course.app_medic.repositories.IGenericRepo;
import com.course.app_medic.repositories.IMedicRepo;
import com.course.app_medic.services.IMedicService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicServiceImpl extends CRUDImpl<Medic, Integer> implements IMedicService {

    private final IMedicRepo repo;

    @Override
    protected IGenericRepo<Medic, Integer> getRepo() {
        return repo;
    }

}
