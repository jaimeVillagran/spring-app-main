package com.course.app_medic.controllers;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LocaleResolver localeResolver; // para resolver los idiomas (locations)
    private final HttpServletRequest httpServletRequest; // representación de la solicitud
    private final HttpServletResponse httpServletResponse; // representación del response

    @GetMapping("/locale/{loc}")
    public ResponseEntity<Void> changeLocale(@PathVariable("loc") String loc) {

        Locale userLocale = switch (loc) {
            case "en" -> Locale.ENGLISH;
            case "fr" -> Locale.FRENCH;
            default -> Locale.ROOT; // toma el messages.properties
        };
        localeResolver.setLocale(httpServletRequest, httpServletResponse, userLocale);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
