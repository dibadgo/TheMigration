package com.example.dibadgo.TheMigration.dataSource;

import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkloadDataSource {

    List<Workload> getAll();

    Optional<Workload> get(UUID id);

    Workload saveWorkload(Workload workload) throws WorkloadException;

    void delete(UUID id);
}
