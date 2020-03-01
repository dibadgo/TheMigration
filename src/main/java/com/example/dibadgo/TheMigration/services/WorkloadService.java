package com.example.dibadgo.TheMigration.services;

import com.example.dibadgo.TheMigration.dataSource.WorkloadDataSource;
import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.InstanceNotFoundException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.PersistentExceptionSupplier;
import com.example.dibadgo.TheMigration.repositoryes.WorkloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
     * @throws InstanceNotFoundException Exception if workload not found
     */
    @Override
    public Workload get(@NotNull UUID id) throws InstanceNotFoundException {
        return workloadRepository.findById(id)
                .orElseThrow(new PersistentExceptionSupplier(new InstanceNotFoundException(id)));
    }

    /**
     * Returns all Workloads from the storage
     *
     * @return Workload List
     */
    @Override
    public List<Workload> getAll() throws InstanceNotFoundException {
        List<Workload> list = new ArrayList<>();
        workloadRepository.findAll().forEach(list::add);
        if (list.isEmpty()) {
            throw new InstanceNotFoundException("Here is no Workloads");
        }
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
                throw new WorkloadException("The source with the same IP is already exists");
            }
            workload.setId(UUID.randomUUID());
        }

        return workloadRepository.save(workload);
    }

    /**
     * Search the workload by IP address
     *
     * @param ip IP address (111.111.111.111)
     * @return Workload model or null
     */
    @Nullable
    @Override
    public Workload getWorkloadByIp(String ip) {
        for (Workload workload : workloadRepository.findAll()) {
            if (workload.getIpAddress().equals(ip)) {
                return workload;
            }
        }
        return null;
    }

    /**
     * Remove an existing Workload
     *
     * @param id Workload Id
     */
    public void delete(UUID id) throws InstanceNotFoundException {
        if (!workloadRepository.existsById(id)) {
            throw new InstanceNotFoundException(id);
        }
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
