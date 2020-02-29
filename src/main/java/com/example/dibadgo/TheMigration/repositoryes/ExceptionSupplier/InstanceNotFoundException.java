package com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class InstanceNotFoundException extends PersistentException {

    public InstanceNotFoundException(@NotNull UUID id) {
        super(String.format("Model with id %s was not found", id.toString()));
    }

    public InstanceNotFoundException(String message) {
        super(message);
    }
}
