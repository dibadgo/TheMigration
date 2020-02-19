package com.example.dibadgo.TheMigration.persistent.repositoryes;

import com.example.dibadgo.TheMigration.model.Workload;
import com.example.dibadgo.TheMigration.persistent.CassandraConnector;
import com.example.dibadgo.TheMigration.persistent.base.Repository;
import com.example.dibadgo.TheMigration.persistent.base.StoredModel;

import java.util.ArrayList;
import java.util.UUID;

public abstract class BaseRepository<T extends StoredModel> implements Repository<T> {
    protected CassandraConnector connector;

    public BaseRepository(CassandraConnector connector) {
        this.connector = connector;
    }

    @Override
    public void add(T item) {

    }

    @Override
    public Workload get(UUID item) {
        return null;
    }

    @Override
    public void update(T item) {

    }

    @Override
    public void delete(T item) {

    }

    @Override
    public ArrayList<T> list() {
        return null;
    }
}
