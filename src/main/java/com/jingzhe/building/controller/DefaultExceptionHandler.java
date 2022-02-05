package com.jingzhe.building.controller;

import com.jingzhe.building.api.model.ApiError;
import com.jingzhe.building.exception.GeoIntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler({
            ConstraintViolationException.class,
            ServerWebInputException.class
    })
    private ResponseEntity<Object> handleInvalidDataException(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        log.info("Bad request:{}", apiError.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(DataAccessException.class)
    private ResponseEntity<Object> handleVerificationException(DataAccessException ex) {
        log.info("Database access exception:", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(GeoIntegrationException.class)
    private ResponseEntity<Object> handleGeoException(GeoIntegrationException ex) {
        log.info("Geo data integration exception:", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleUnhandledException(Exception ex) {
        log.warn("Unhandled exception:", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
