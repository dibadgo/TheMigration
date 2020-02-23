package com.example.dibadgo.TheMigration.services;

import com.example.dibadgo.TheMigration.dataSource.WorkloadDataSource;
import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.InstancePersistentNotFoundException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.PersistentException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.PersistentExceptionSupplier;
import com.example.dibadgo.TheMigration.repositoryes.WorkloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkloadService implements WorkloadDataSource {

    private WorkloadRepository workloadRepository;

    @Autowired
    public WorkloadService(WorkloadRepository workloadRepository) {
        this.workloadRepository = workloadRepository;
    }

    @Override
    public Workload get(UUID id) {
        return workloadRepository.findById(id)
                .orElseThrow(new PersistentExceptionSupplier(new InstancePersistentNotFoundException(id)));
    }

    @Override
    public List<Workload> getAll() {
        List<Workload> list = new ArrayList<Workload>();
        workloadRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Workload saveWorkload(Workload workload) throws WorkloadException {
        if (workload.getId() != null) {
            Optional<Workload> existedWorkload = workloadRepository.findById(workload.getId());
            if (existedWorkload.isPresent() && !existedWorkload.get().getIpAddress().equals(workload.getIpAddress()))
                throw new WorkloadException("You cannot modify an existing IP address on a source");
        } else {
            if (checkIpAddress(workload.getIpAddress())) {
                throw new PersistentException("The source with the same IP is already exists");
            }
            workload.setId(UUID.randomUUID());
        }

        return workloadRepository.save(workload);
    }

    public void delete(UUID id) {
        workloadRepository.deleteById(id);
    }

    private boolean checkIpAddress(String ipAddress) {
        for (Workload workload : workloadRepository.findAll()) {
            if (workload.getIpAddress().equals(ipAddress)) {
                return true;
            }
        }
        return false;
    }
}
