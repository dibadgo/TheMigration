package com.example.dibadgo.TheMigration.service;

import com.example.dibadgo.TheMigration.dataSource.WorkloadDataSource;
import com.example.dibadgo.TheMigration.domain.Credentials;
import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.InstanceNotFoundException;
import com.example.dibadgo.TheMigration.repositoryes.WorkloadRepository;
import com.example.dibadgo.TheMigration.services.WorkloadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WorkloadServiceTests {

    WorkloadDataSource workloadDataSource;
    WorkloadRepository workloadRepository;

    @BeforeEach
    public void setUp() {
        workloadRepository = Mockito.mock(WorkloadRepository.class);
        workloadDataSource = new WorkloadService(workloadRepository);
    }

    @Test
    public void workloadShouldThrowExceptionWhenItDoesNotExists() {
        Mockito.when(workloadRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(InstanceNotFoundException.class, () -> workloadDataSource.get(UUID.randomUUID()));

        Assertions.assertThrows(InstanceNotFoundException.class, () -> workloadDataSource.getAll());
    }

    @Test
    public void saveDataBaseWasCalled() {
        Workload workload = generateWorkload("192.168.0.1");

        Mockito.when(workloadRepository.findAll())
                .thenReturn(List.of());

        Mockito.when(workloadRepository.save(workload))
                .thenReturn(workload);

        Workload savedWorkload = workloadDataSource.saveWorkload(workload);

        Assertions.assertNotNull(savedWorkload.getId());
        Mockito.verify(workloadRepository, Mockito.times(1)).save(workload);
    }

    @Test
    public void cannotModifyIpAddress() {
        // Prepare of an exist Workload
        UUID workloadId = UUID.randomUUID();

        Workload existWorkload = generateWorkload("192.168.0.1");

        existWorkload.setId(workloadId);

        Mockito.when(workloadRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(existWorkload));

        // Prepare of a new Workload
        Workload workload = generateWorkload("192.168.0.2");
        workload.setId(workloadId);

        Assertions.assertThrows(WorkloadException.class, () -> workloadDataSource.saveWorkload(workload));
    }

    @Test
    public void ipAddressAlreadyExists() {
        Workload workload = generateWorkload("192.168.0.1");

        Mockito.when(workloadRepository.findAll())
                .thenReturn(List.of(workload));

        Assertions.assertThrows(WorkloadException.class, () -> workloadDataSource.saveWorkload(workload));
    }

    @Test
    public void deleteMethodWasCalled() {
        UUID workloadId = UUID.randomUUID();

        Mockito.when(workloadRepository.existsById(workloadId))
                .thenReturn(true);
        Mockito.doNothing().when(workloadRepository).deleteById(workloadId);

        workloadDataSource.delete(workloadId);

        Mockito.verify(workloadRepository, Mockito.times(1)).deleteById(workloadId);
    }

    private Workload generateWorkload(String iAddress) {
        return new Workload(
                iAddress,
                new Credentials("pass", "user", "")
        );
    }
}
