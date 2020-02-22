package com.example.dibadgo.TheMigration.dataSource;

import com.example.dibadgo.TheMigration.domain.Workload;

import java.util.ArrayList;
import java.util.List;

public interface WorkloadDataSource {

    List<Workload> getAll();

    Workload saveWorkload(Workload workload);
}
