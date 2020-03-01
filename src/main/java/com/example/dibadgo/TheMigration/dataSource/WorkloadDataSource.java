package com.example.dibadgo.TheMigration.dataSource;

import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.InstanceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface WorkloadDataSource {

    List<Workload> getAll() throws InstanceNotFoundException;

    Workload get(UUID id) throws InstanceNotFoundException;

    Workload saveWorkload(Workload workload) throws WorkloadException;

    void delete(UUID id) throws InstanceNotFoundException;

    Workload getWorkloadByIp(String ip);
}
