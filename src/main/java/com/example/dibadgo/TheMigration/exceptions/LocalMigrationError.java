package com.example.dibadgo.TheMigration.exceptions;

public class LocalMigrationError extends RuntimeException {

    public LocalMigrationError(String message) {
        super(message);
    }

    public LocalMigrationError(String message, Throwable cause) {
        super(message, cause);
    }
}
