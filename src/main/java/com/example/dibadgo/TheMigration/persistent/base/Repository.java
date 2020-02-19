package com.example.dibadgo.TheMigration.persistent.base;

import com.example.dibadgo.TheMigration.model.Workload;

import java.util.ArrayList;
import java.util.UUID;

public interface Repository<T extends StoredModel> {
    void add(T item);

    Workload get(UUID item);

    void update(T item);

    void delete(T item);

    ArrayList<T> list();
}
