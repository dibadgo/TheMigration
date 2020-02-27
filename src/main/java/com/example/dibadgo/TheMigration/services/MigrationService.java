package com.example.dibadgo.TheMigration.services;

import com.example.dibadgo.TheMigration.dataSource.MigrationDataSource;
import com.example.dibadgo.TheMigration.domain.Migration;
import com.example.dibadgo.TheMigration.domain.MigrationBind;
import com.example.dibadgo.TheMigration.domain.TargetCloud;
import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.InstancePersistentNotFoundException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.PersistentExceptionSupplier;
import com.example.dibadgo.TheMigration.repositoryes.MigrationRepository;
import com.example.dibadgo.TheMigration.repositoryes.WorkloadRepository;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @see WorkloadRepository
     */
    private WorkloadRepository workloadRepository;

    /**
     * Constructor
     *
     * @param migrationRepository Migration repository (Data store)
     * @param workloadRepository  Workload repository (Data store)
     */
    @Autowired
    public MigrationService(MigrationRepository migrationRepository, WorkloadRepository workloadRepository) {
        this.migrationRepository = migrationRepository;
        this.workloadRepository = workloadRepository;
    }

    /**
     * Create a migration from MigrationBind
     *
     * @param migrationBind Migration bind model
     * @return Migration model
     * @see MigrationBind
     */
    @NotNull
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
    public Migration update(@NotNull UUID migrationId, @NotNull MigrationBind migrationBind) {
        Migration migration = makeMigrationFrom(migrationBind);
        migration.setId(migrationId);

        Migration existMigration = migrationRepository.findById(migrationId)
                .orElseThrow(new PersistentExceptionSupplier(
                                String.format("The migration with ID %s was not found", migrationId.toString())
                        )
                );

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
    public Migration update(@NotNull Migration migration) {

        Workload source = migration.getSource();
        Workload target = migration.getTargetCloud().getTarget();

        workloadRepository.save(source);
        workloadRepository.save(target);

        return migrationRepository.save(migration);
    }

    /**
     * Search of a Migration by Id
     *
     * @param migrationId Migration id
     * @return Migration model
     * @throws InstancePersistentNotFoundException
     */
    public Migration get(@NotNull UUID migrationId) throws InstancePersistentNotFoundException {
        Migration migration = migrationRepository.findById(migrationId)
                .orElseThrow(new PersistentExceptionSupplier(new InstancePersistentNotFoundException(migrationId)));
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
        List<Migration> list = new ArrayList<Migration>();
        for (Migration migration : migrationRepository.findAll()) {
            loadWorkloadFor(migration);
            list.add(migration);
        }
        return list;
    }

    /**
     * Delete of an existing migration by id
     *
     * @param id Migration id
     */
    public void delete(@NotNull UUID id) {
        migrationRepository.deleteById(id);
    }

    private void loadWorkloadFor(@NotNull Migration migration) {
        Workload source = workloadRepository.findById(migration.getSourceId()).orElseThrow();
        migration.setSource(source);

        Workload target = workloadRepository.findById(migration.getTargetCloud().getTargetId()).orElseThrow();
        migration.getTargetCloud().setTarget(target);
    }

    private Migration makeMigrationFrom(@NotNull MigrationBind migrationBind) {
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
