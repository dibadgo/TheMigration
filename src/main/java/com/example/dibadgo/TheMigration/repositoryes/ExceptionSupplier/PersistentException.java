package com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier;

public class PersistentException extends RuntimeException {

    public PersistentException() {
    }

    public PersistentException(String message) {
        super(message);
    }
}
