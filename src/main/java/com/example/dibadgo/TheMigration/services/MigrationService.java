package com.example.dibadgo.TheMigration.services;

import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.dataSource.MigrationDataSource;
import com.example.dibadgo.TheMigration.domain.Migration;
import com.example.dibadgo.TheMigration.domain.MigrationBind;
import com.example.dibadgo.TheMigration.domain.TargetCloud;
import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.exceptions.LocalMigrationError;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.InstancePersistentNotFoundException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.PersistentExceptionSupplier;
import com.example.dibadgo.TheMigration.repositoryes.MigrationRepository;
import com.example.dibadgo.TheMigration.repositoryes.WorkloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MigrationService implements MigrationDataSource {

    private MigrationRepository migrationRepository;
    private WorkloadRepository workloadRepository;

    @Autowired
    public MigrationService(MigrationRepository migrationRepository, WorkloadRepository workloadRepository) {
        this.migrationRepository = migrationRepository;
        this.workloadRepository = workloadRepository;
    }

    public Migration create(MigrationBind migrationBind) {
        Migration migration = makeMigrationFrom(migrationBind);
        migration.setId(UUID.randomUUID());

        return migrationRepository.save(migration);
    }

    public Migration update(UUID migrationId, MigrationBind migrationBind) {
        Migration migration = makeMigrationFrom(migrationBind);
        migration.setId(migrationId);

        Migration existMigration = migrationRepository.findById(migrationId)
                .orElseThrow(new PersistentExceptionSupplier(
                                String.format("The migration with ID %s was not found", migrationId.toString())
                        )
                );

        return migrationRepository.save(migration);
    }

    public Migration update(Migration migration) {

        Workload source = migration.getSource();
        Workload target = migration.getTargetCloud().getTarget();

        workloadRepository.save(source);
        workloadRepository.save(target);

        return migrationRepository.save(migration);
    }

    public Migration get(UUID migrationId) {
        Migration migration = migrationRepository.findById(migrationId)
                .orElseThrow(new PersistentExceptionSupplier(new InstancePersistentNotFoundException(migrationId)));
        loadWorkloadFor(migration);
        return migration;
    }

    @Override
    public List<Migration> getAll() {
        List<Migration> list = new ArrayList<Migration>();
        for (Migration migration : migrationRepository.findAll()) {
            loadWorkloadFor(migration);
            list.add(migration);
        }
        return list;
    }

    public void delete(UUID id) {
        migrationRepository.deleteById(id);
    }

    private void loadWorkloadFor(Migration migration) {
        Workload source = workloadRepository.findById(migration.getSourceId()).orElseThrow();
        migration.setSource(source);

        Workload target = workloadRepository.findById(migration.getTargetCloud().getTargetId()).orElseThrow();
        migration.getTargetCloud().setTarget(target);
    }

    private Migration makeMigrationFrom(MigrationBind migrationBind) {
        Workload source = workloadRepository.findById(migrationBind.getSourceId()).orElseThrow();
        Workload target = workloadRepository.findById(migrationBind.getTargetId()).orElseThrow();

        TargetCloud targetCloud = new TargetCloud(
                migrationBind.getTargetCloud(),
                migrationBind.getCloudCredentials(),
                target.getId()
        );

        targetCloud.setTarget(target);

        var migration = new Migration(
                migrationBind.getMountPoints(),
                source.getId(),
                targetCloud,
                migrationBind.getState(),
                migrationBind.getOsType()
        );

        migration.setSource(source);

        return migration;
    }
}
