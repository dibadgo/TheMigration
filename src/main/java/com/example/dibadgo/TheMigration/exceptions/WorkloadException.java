package com.example.dibadgo.TheMigration.exceptions;

/**
 * The standard error describes unexpectable operations with Workload
 */
public class WorkloadException extends RuntimeException {
    public WorkloadException() {
    }

    public WorkloadException(String message) {
        super(message);
    }
}
