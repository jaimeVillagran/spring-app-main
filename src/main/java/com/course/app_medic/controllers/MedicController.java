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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.app_medic.dtos.MedicDTO;
//import com.course.app_medic.dtos.MedicRecord;
import com.course.app_medic.models.Medic;
import com.course.app_medic.services.IMedicService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/medics")
@RequiredArgsConstructor
public class MedicController {

    private final IMedicService service;

    @Qualifier("medicMapper")
    private final ModelMapper mapper;

    private MedicDTO convertToDto(Medic obj) {
        return mapper.map(obj, MedicDTO.class);
    }

    private Medic convertToEntity(MedicDTO dto) {
        return mapper.map(dto, Medic.class);
    }

    @PreAuthorize("@authorizeLogic.hasAccess('findAll')")
    // @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<MedicDTO>> findAll() {
        // forma 1: dto sin control de lambdas

        List<MedicDTO> list = service.findAll().stream().map(e -> convertToDto(e)).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);

        // forma 2: dto con lambdas
        /*
         * List<MedicDTO> list =
         * service.findAll().stream().map(this::convertToDto).toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */

        // forma 3: con records
        /*
         * List<MedicRecord> list = service.findAll()
         * .stream().map(e -> new MedicRecord(e.getIdMedic(), e.getFirstName(),
         * e.getLastName(), e.getDni(),
         * e.getAddress(), e.getPhone(), e.getEmail()))
         * .toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */

    }

    @PostMapping
    public ResponseEntity<MedicDTO> save(@Valid @RequestBody MedicDTO dto) {
        Medic obj = service.save(convertToEntity(dto));
        // return new ResponseEntity<>(obj, HttpStatus.CREATED);
        // patientes/1
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedic())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable("id") Integer id) {
        Medic obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medic> update(@Valid @RequestBody MedicDTO dto, @PathVariable("id") Integer id)
            throws Exception {
        Medic obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Medic> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // nivel 3: opcional
    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicDTO> findByHateoas(@PathVariable("id") Integer id) {
        EntityModel<MedicDTO> resource = EntityModel.of(convertToDto(service.findById(id))); // modelo de salida

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id)); // generación del link

        resource.add(link1.withRel("patient-info1")); // agregamos el link a una llave
        resource.add(link1.withRel("patient-info2")); // agregamos el link a una llave

        return resource;
    }

}
