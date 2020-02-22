package com.example.dibadgo.TheMigration.services;

import com.example.dibadgo.TheMigration.dataSource.WorkloadDataSource;
import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.repositoryes.WorkloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkloadService implements WorkloadDataSource {

    private WorkloadRepository workloadRepository;

    @Autowired
    public WorkloadService(WorkloadRepository workloadRepository) {
        this.workloadRepository = workloadRepository;
    }

    @Override
    public List<Workload> getAll() {
        List<Workload> list = new ArrayList<Workload>();
        workloadRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Workload saveWorkload(Workload workload) {
        return workloadRepository.save(workload);
    }
}
