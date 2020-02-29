package com.example.dibadgo.TheMigration.controllers;

import com.example.dibadgo.TheMigration.exceptions.LocalMigrationError;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.InstanceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;

/**
 * This is main point to handle oll custom exception in an application
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WorkloadException.class)
    protected ResponseEntity<CustomErrorBody> handleWorkloadException(@NotNull WorkloadException exception) {
        return new ResponseEntity<>(
                new CustomErrorBody(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(LocalMigrationError.class)
    protected ResponseEntity<CustomErrorBody> handleLocalMigrationError(@NotNull LocalMigrationError localMigrationError) {
        return new ResponseEntity<>(
                new CustomErrorBody(localMigrationError.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InstanceNotFoundException.class)
    protected ResponseEntity<CustomErrorBody> handleInstancePersistentNotFoundException(@NotNull InstanceNotFoundException ex) {
        return new ResponseEntity<>(
                new CustomErrorBody(ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Predictably error body for all exceptions
     */
    private static class CustomErrorBody {
        private String message;

        public CustomErrorBody(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
