package com.course.app_medic.controllers;

import java.net.URI;
//import java.util.List;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.app_medic.dtos.SpecialtyDTO;
//import com.course.app_medic.dtos.SpecialtyRecord;
import com.course.app_medic.models.Specialty;
import com.course.app_medic.services.ISpecialtyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/specialtys")
@RequiredArgsConstructor
public class SpecialtyController {

    private final ISpecialtyService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    // transformers

    private SpecialtyDTO convertToDto(Specialty obj) {
        return mapper.map(obj, SpecialtyDTO.class);
    }

    private Specialty convertToEntity(SpecialtyDTO dto) {
        return mapper.map(dto, Specialty.class);
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> findAll() {
        // forma 1: dto sin control de lambdas

        List<SpecialtyDTO> list = service.findAll().stream().map(e -> convertToDto(e)).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);

        // forma 2: dto con lambdas
        /*
         * List<SpecialtyDTO> list =
         * service.findAll().stream().map(this::convertToDto).toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */

        // forma 3: con records
        /*
         * List<SpecialtyRecord> list = service.findAll()
         * .stream().map(e -> new SpecialtyRecord(e.getIdSpecialty(), e.getFirstName(),
         * e.getLastName(), e.getDni(),
         * e.getAddress(), e.getPhone(), e.getEmail()))
         * .toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */

    }

    @PostMapping
    public ResponseEntity<SpecialtyDTO> save(@Valid @RequestBody SpecialtyDTO dto) {
        Specialty obj = service.save(convertToEntity(dto));
        // return new ResponseEntity<>(obj, HttpStatus.CREATED);
        // patientes/1
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getIdSpecialty())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable("id") Integer id) {
        Specialty obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialty> update(@Valid @RequestBody SpecialtyDTO dto, @PathVariable("id") Integer id)
            throws Exception {
        Specialty obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Specialty> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // nivel 3: opcional
    @GetMapping("/hateoas/{id}")
    public EntityModel<SpecialtyDTO> findByHateoas(@PathVariable("id") Integer id) {
        EntityModel<SpecialtyDTO> resource = EntityModel.of(convertToDto(service.findById(id))); // modelo de salida

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id)); // generación del link

        resource.add(link1.withRel("patient-info1")); // agregamos el link a una llave
        resource.add(link1.withRel("patient-info2")); // agregamos el link a una llave

        return resource;
    }

    /*
     * @GetMapping("/pageable")
     * public ResponseEntity<?> listByPage() {
     * 
     * }
     */

}
