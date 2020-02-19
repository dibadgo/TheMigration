package com.example.dibadgo.TheMigration.persistent.base;

import java.io.Serializable;
import java.util.UUID;

public interface StoredModel extends Serializable {
    UUID getId();

    void setId(UUID id);
}
