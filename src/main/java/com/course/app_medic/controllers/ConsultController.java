package com.course.app_medic.controllers;

import java.net.URI;
import java.time.LocalDateTime;
//import java.util.List;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.app_medic.dtos.ConsultDTO;
import com.course.app_medic.dtos.ConsultListExamDTO;
import com.course.app_medic.dtos.ConsultProcDTO;
import com.course.app_medic.dtos.FilterConsultDTO;
import com.course.app_medic.dtos.IConsultProcDTO;
//import com.course.app_medic.dtos.ConsultDTO;
//import com.course.app_medic.dtos.ConsultRecord;
import com.course.app_medic.models.Consult;
import com.course.app_medic.models.Exam;
import com.course.app_medic.services.IConsultService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/consults")
@RequiredArgsConstructor
public class ConsultController {

    private final IConsultService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    // transformers

    private ConsultDTO convertToDto(Consult obj) {
        return mapper.map(obj, ConsultDTO.class);
    }

    private Consult convertToEntity(ConsultDTO dto) {
        return mapper.map(dto, Consult.class);
    }

    @GetMapping
    public ResponseEntity<List<ConsultDTO>> findAll() {
        // forma 1: dto sin control de lambdas

        List<ConsultDTO> list = service.findAll().stream().map(e -> convertToDto(e)).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);

        // forma 2: dto con lambdas
        /*
         * List<ConsultDTO> list =
         * service.findAll().stream().map(this::convertToDto).toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */

        // forma 3: con records
        /*
         * List<ConsultRecord> list = service.findAll()
         * .stream().map(e -> new ConsultRecord(e.getIdConsult(), e.getFirstName(),
         * e.getLastName(), e.getDni(),
         * e.getAddress(), e.getPhone(), e.getEmail()))
         * .toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */

    }

    @PostMapping
    public ResponseEntity<ConsultDTO> save(@Valid @RequestBody ConsultListExamDTO dto) {
        Consult cons = this.convertToEntity(dto.getConsult());
        // patientes/1
        List<Exam> exams = mapper.map(dto.getLstExam(), new TypeToken<List<Exam>>() {
        }.getType());
        Consult obj = service.saveTransactional(cons, exams);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> findById(@PathVariable("id") Integer id) {
        Consult obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consult> update(@Valid @RequestBody ConsultDTO dto, @PathVariable("id") Integer id)
            throws Exception {
        Consult obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Consult> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // nivel 3: opcional
    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultDTO> findByHateoas(@PathVariable("id") Integer id) {
        EntityModel<ConsultDTO> resource = EntityModel.of(convertToDto(service.findById(id))); // modelo de salida

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id)); // generación del link

        resource.add(link1.withRel("patient-info1")); // agregamos el link a una llave
        resource.add(link1.withRel("patient-info2")); // agregamos el link a una llave

        return resource;
    }

    @PostMapping("/search/others")
    public ResponseEntity<List<ConsultDTO>> searchByOthers(@RequestBody FilterConsultDTO filterDTO) {
        List<Consult> consults = service.search(filterDTO.getDni(), filterDTO.getFullname());

        // forma convencional
        List<ConsultDTO> consultDTOs = consults.stream().map(e -> mapper.map(e,
                ConsultDTO.class)).toList();
        // forma moderna
        /*
         * List<ConsultDTO> consultDTOs = mapper.map(consults, new
         * TypeToken<List<ConsultDTO>>() {
         * }.getType());
         */

        return new ResponseEntity<>(consultDTOs, HttpStatus.OK);
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<ConsultDTO>> searchByDates(
            @RequestParam(value = "date1", defaultValue = "2024-08-08", required = true) String date1,
            @RequestParam(value = "date2", required = true) String date2) {
        List<Consult> consults = service.searchByDates(LocalDateTime.parse(date1), LocalDateTime.parse(date2));
        List<ConsultDTO> consultDTOs = mapper.map(consults, new TypeToken<List<ConsultDTO>>() {
        }.getType());
        return new ResponseEntity<>(consultDTOs, HttpStatus.OK);
    }

    @GetMapping("/callProcedureNative")
    public ResponseEntity<List<ConsultProcDTO>> callProcedureOrFunctionNative() {
        List<ConsultProcDTO> consults = service.callProcedureOrFunctionNative();
        return new ResponseEntity<>(consults, HttpStatus.OK);
    }

    @GetMapping("/callProcedureProjection")
    public ResponseEntity<List<IConsultProcDTO>> callProcedureOrFunctionProjection() {
        List<IConsultProcDTO> consults = service.callProcedureOrFunctionProjection();
        return new ResponseEntity<>(consults, HttpStatus.OK);
    }

    @GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReport() throws Exception {
        byte[] data = service.generateReport();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
