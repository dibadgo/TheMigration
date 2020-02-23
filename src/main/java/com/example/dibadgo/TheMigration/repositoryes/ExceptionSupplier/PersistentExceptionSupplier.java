package com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier;

import java.util.function.Supplier;

public class PersistentExceptionSupplier implements Supplier<PersistentException> {

    PersistentException exception;

    public PersistentExceptionSupplier(String message) {
        exception = new PersistentException(message);
    }

    public PersistentExceptionSupplier(PersistentException exception) {
        this.exception = exception;
    }

    @Override
    public PersistentException get() {
        return exception;
    }
}
