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

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The service with the business logic for Workloads
 */
@Service
public class WorkloadService implements WorkloadDataSource {

    /**
     * Workload repository
     *
     * @see WorkloadRepository
     */
    private WorkloadRepository workloadRepository;

    /**
     * Constructor
     *
     * @param workloadRepository Workload repository
     */
    @Autowired
    public WorkloadService(WorkloadRepository workloadRepository) {
        this.workloadRepository = workloadRepository;
    }

    /**
     * Search for existing workload by id
     *
     * @param id Workload Id
     * @return Workload
     * @throws InstancePersistentNotFoundException Exception if workload not found
     */
    @Override
    public Workload get(@NotNull UUID id) throws InstancePersistentNotFoundException {
        return workloadRepository.findById(id)
                .orElseThrow(new PersistentExceptionSupplier(new InstancePersistentNotFoundException(id)));
    }

    /**
     * Returns all Workloads from the storage
     *
     * @return Workload List
     */
    @Override
    public List<Workload> getAll() {
        List<Workload> list = new ArrayList<Workload>();
        workloadRepository.findAll().forEach(list::add);
        return list;
    }

    /**
     * Create or update a workload
     *
     * @param workload Workload
     * @return Saved workload
     * @throws WorkloadException Exception if found uexpactable behaviour
     */
    @Override
    public Workload saveWorkload(@NotNull Workload workload) throws WorkloadException {
        if (workload.getId() != null) {
            Optional<Workload> existedWorkload = workloadRepository.findById(workload.getId());
            if (existedWorkload.isPresent() && !existedWorkload.get().getIpAddress().equals(workload.getIpAddress()))
                throw new WorkloadException("You cannot modify an existing IP address on existing source");
        } else {
            if (checkIpAddress(workload.getIpAddress())) {
                throw new PersistentException("The source with the same IP is already exists");
            }
            workload.setId(UUID.randomUUID());
        }

        return workloadRepository.save(workload);
    }

    /**
     * Remove an existing Workload
     *
     * @param id Workload Id
     */
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
