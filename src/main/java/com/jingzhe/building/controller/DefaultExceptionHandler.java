package com.jingzhe.building.controller;

import com.jingzhe.building.api.model.ApiError;
import com.jingzhe.building.exception.BuildingNotFoundException;
import com.jingzhe.building.exception.BuildingServiceException;
import com.jingzhe.building.exception.GeoIntegrationException;
import com.jingzhe.building.exception.InvalidInputDataException;
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
            ServerWebInputException.class,
            InvalidInputDataException.class
    })
    private ResponseEntity<Object> handleInvalidDataException(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        log.info("Bad request:{}", apiError.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({
            BuildingNotFoundException.class
    })
    private ResponseEntity<Object> handleNotFoundException(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        log.info("Not found:{}", apiError.toString());
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

    @ExceptionHandler(BuildingServiceException.class)
    private ResponseEntity<Object> handleServiceException(BuildingServiceException ex) {
        log.info("Building service exception:", ex);
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
