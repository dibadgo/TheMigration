package com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier;

/**
 * Exception describes unexpectable behaviour in a persistence layer
 */
public class PersistentException extends RuntimeException {

    public PersistentException() {
    }

    public PersistentException(String message) {
        super(message);
    }
}
