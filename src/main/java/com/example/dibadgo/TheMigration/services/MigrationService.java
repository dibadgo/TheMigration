package com.example.dibadgo.TheMigration.services;

import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.dataSource.MigrationDataSource;
import com.example.dibadgo.TheMigration.dataSource.WorkloadDataSource;
import com.example.dibadgo.TheMigration.domain.*;
import com.example.dibadgo.TheMigration.exceptions.LocalMigrationError;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.InstanceNotFoundException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.PersistentExceptionSupplier;
import com.example.dibadgo.TheMigration.repositoryes.MigrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This service contains all bossiness logic
 * for work with Migration model
 *
 * @see Migration
 */
@Service
public class MigrationService implements MigrationDataSource {

    /**
     * Migration repository (Data store)
     *
     * @see MigrationRepository
     */
    private MigrationRepository migrationRepository;
    /**
     * Workload repository (Data store)
     *
     * @see WorkloadService
     */
    private WorkloadDataSource workloadService;

    /**
     * Constructor
     *
     * @param migrationRepository Migration repository (Data store)
     * @param workloadService     Workload service (Data source)
     */
    @Autowired
    public MigrationService(MigrationRepository migrationRepository, WorkloadService workloadService) {
        this.migrationRepository = migrationRepository;
        this.workloadService = workloadService;
    }

    /**
     * Create a migration from MigrationBind
     *
     * @param migrationBind Migration bind model
     * @return Migration model
     * @see MigrationBind
     */
    @NotNull
    @Override
    public Migration create(@NotNull MigrationBind migrationBind) {
        Migration migration = makeMigrationFrom(migrationBind);
        migration.setId(UUID.randomUUID());

        return migrationRepository.save(migration);
    }

    /**
     * Update of an exists migration by Id
     * from a migration bind
     *
     * @param migrationId   Migration Id
     * @param migrationBind Parameters to update
     * @return Updated Migration model
     */
    @NotNull
    @Override
    public Migration update(@NotNull UUID migrationId, @NotNull MigrationBind migrationBind) {
        if (!migrationRepository.existsById(migrationId)) {
            throw new InstanceNotFoundException(migrationId);
        }

        Migration migration = makeMigrationFrom(migrationBind);
        migration.setId(migrationId);

        return migrationRepository.save(migration);
    }

    /**
     * Update of an existing migration from
     * current state of a Migration
     * <p>
     * Also this method will update the source and target workloads
     *
     * @param migration Migration model
     * @return Updated migration
     */
    @NotNull
    @Override
    public Migration update(@NotNull Migration migration) throws WorkloadException {

        Workload source = migration.getSource();
        Workload target = migration.getTargetCloud().getTarget();

        workloadService.saveWorkload(source);
        workloadService.saveWorkload(target);

        return migrationRepository.save(migration);
    }

    /**
     * Search of a Migration by Id
     *
     * @param migrationId Migration id
     * @return Migration model
     * @throws InstanceNotFoundException Exception, If the migration not found
     */
    @Override
    public Migration get(@NotNull UUID migrationId) throws InstanceNotFoundException {
        Migration migration = migrationRepository.findById(migrationId)
                .orElseThrow(new PersistentExceptionSupplier(new InstanceNotFoundException(migrationId)));
        loadWorkloadFor(migration);
        return migration;
    }

    /**
     * Returns a list of an existing migrations in a persistence layer
     *
     * @return List of the migrations.
     */
    @Override
    public List<Migration> getAll() {
        List<Migration> list = new ArrayList<>();
        for (Migration migration : migrationRepository.findAll()) {
            loadWorkloadFor(migration);
            list.add(migration);
        }
        if (list.isEmpty()) {
            throw new InstanceNotFoundException("Here is no Migrations");
        }
        return list;
    }

    /**
     * Delete of an existing migration by id
     *
     * @param id Migration id
     */
    @Override
    public void delete(@NotNull UUID id) {
        migrationRepository.deleteById(id);
    }

    /**
     * Make migration process in a background thread
     * @param migrationId Migration Id
     * @param taskExecutor Task pool executor
     */
    @Override
    public void run(UUID migrationId, ThreadPoolTaskExecutor taskExecutor) {
        Migration migration = this.get(migrationId);
        if (migration.getState() != State.PENDING)
            throw new LocalMigrationError("Cannot start the migration when state is not pending");

        MigrationRunner migrationRunner = new MigrationRunner(this, migration);
        taskExecutor.execute(migrationRunner);
    }

    private void loadWorkloadFor(@NotNull Migration migration) {
        Workload source = workloadService.get(migration.getSourceId());
        migration.setSource(source);

        Workload target = workloadService.get(migration.getTargetCloud().getTargetId());
        migration.getTargetCloud().setTarget(target);
    }

    private Migration makeMigrationFrom(@NotNull MigrationBind migrationBind) {
        Workload source = workloadService.getWorkloadByIp(migrationBind.getSourceWorkloadIp());
        Workload target = workloadService.getWorkloadByIp(migrationBind.getTargetWorkloadIp());

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
