package com.course.app_medic.exception;

//import java.net.URI;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    // para capturar cualquiera otra excepción en el servidor. Disponible para cualquier versión de Spring
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleAllException(Exception ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Alternativa 1: para excepciones custom. Versiones para spring 1.5 en adelante
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleModelNotFoundException(
            ModelNotFoundException ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    // Alternativa 2: por sobreescritura de lógica de excepciones. Versiones para
    // spring 1.5 en adelante
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // permite agrupar todas las iteraciones para el mensaje a partir de la lista de
        // errores
        String msg = ex.getBindingResult().getFieldErrors().stream().map(
                e -> e.getField().concat(":").concat(e.getDefaultMessage().concat(" "))).collect(Collectors.joining());

        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg,
                request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    // Alternativa 2 a la primera, mediante la clase ProblemDetail para versiones
    // desde Spring 3
    /*
     * @ExceptionHandler(ModelNotFoundException.class)
     * public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex,
     * WebRequest request) {
     * ProblemDetail problemDetail =
     * ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
     * // personalización de la salida
     * problemDetail.setTitle("Model Not Found Exception");
     * problemDetail.setType(URI.create("/not-found")); // bloque de info.
     * problemDetail.setProperty("extra1", "extra-info");
     * problemDetail.setProperty("extra2", 32);
     * return problemDetail;
     * }
     */

    // Alternativa 3 a la primera, mediante la clase ErrorResponse para versiones
    // desde Spring 3
    /*
     * @ExceptionHandler(ModelNotFoundException.class)
     * public ErrorResponse handleModelNotFoundException(ModelNotFoundException ex,
     * WebRequest request) {
     * // personalización de la salida
     * return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage())
     * .title("Model Not Found")
     * .type(URI.create(request.getContextPath()))
     * .property("extra1", "extra-info")
     * .property("extra2", 2)
     * .build();
     * 
     * }
     */

}
