package com.example.dibadgo.TheMigration.persistent.repositoryes;

import com.example.dibadgo.TheMigration.model.Workload;
import com.example.dibadgo.TheMigration.persistent.CassandraConnector;

import java.util.ArrayList;
import java.util.UUID;

public class WorkloadRepository extends BaseRepository<Workload> {

    public WorkloadRepository(CassandraConnector connector) {
        super(connector);
    }
}
