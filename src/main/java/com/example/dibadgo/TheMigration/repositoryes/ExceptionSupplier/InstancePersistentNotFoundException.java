package com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier;

import java.util.UUID;

public class InstancePersistentNotFoundException extends PersistentException {

    public InstancePersistentNotFoundException(UUID id) {
        super(String.format("Model with id %s was not found", id.toString()));
    }
}
