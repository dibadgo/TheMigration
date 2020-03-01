package com.example.dibadgo.TheMigration.service;

import com.example.dibadgo.TheMigration.dataSource.WorkloadDataSource;
import com.example.dibadgo.TheMigration.repositoryes.WorkloadRepository;
import com.example.dibadgo.TheMigration.services.WorkloadService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class MigrationTestConfiguration {

//    @Bean
//    public WorkloadDataSource workloadDataSource() {
//        return Mockito.mock(WorkloadService.class);
//    }

    @Bean
    public WorkloadRepository workloadRepository() {
        return Mockito.mock(WorkloadRepository.class);
    }
}
