package com.example.dibadgo.TheMigration.service;

import com.example.dibadgo.TheMigration.base.Cloud;
import com.example.dibadgo.TheMigration.base.OsType;
import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.dataSource.MigrationDataSource;
import com.example.dibadgo.TheMigration.domain.*;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.InstanceNotFoundException;
import com.example.dibadgo.TheMigration.repositoryes.ExceptionSupplier.PersistentException;
import com.example.dibadgo.TheMigration.repositoryes.MigrationRepository;
import com.example.dibadgo.TheMigration.services.MigrationService;
import com.example.dibadgo.TheMigration.services.WorkloadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MigrationServiceTests {

    WorkloadService workloadService;

    MigrationRepository migrationRepository;

    MigrationDataSource migrationDataSource;

    @BeforeEach
    public void setUp() {
        workloadService = Mockito.mock(WorkloadService.class);
        migrationRepository = Mockito.mock(MigrationRepository.class);
        migrationDataSource = new MigrationService(migrationRepository, workloadService);
    }

    @Test
    public void createShouldSetIdAndSaveToDb() {
        MigrationBind bind = new MigrationBind(
                UUID.randomUUID(),
                UUID.randomUUID(),
                Cloud.AWS, new Credentials("passwd", "usr", ""),
                new String[]{"c:"},
                OsType.WINDOWS,
                State.PENDING
        );

        Workload workload = new Workload();

        Mockito.when(workloadService.get(Mockito.any(UUID.class))).thenReturn(workload);
        Mockito.when(migrationRepository.save(Mockito.any(Migration.class))).thenAnswer(new Answer<Migration>() {
            @Override
            public Migration answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Migration) args[0];
            }
        });

        Migration migration = migrationDataSource.create(bind);

        Assertions.assertNotNull(migration.getId());
    }

    @Test
    public void updateMigrationShouldInvokeUpdateMethod() {
        UUID migrationId = UUID.randomUUID();
        MigrationBind bind = new MigrationBind(
                UUID.randomUUID(),
                UUID.randomUUID(),
                Cloud.AWS, new Credentials("passwd", "usr", ""),
                new String[]{"c:"},
                OsType.WINDOWS,
                State.PENDING
        );
        Mockito.when(migrationRepository.existsById(Mockito.any(UUID.class)))
                .thenReturn(true);

        Mockito.when(migrationRepository.save(Mockito.any(Migration.class))).thenAnswer(new Answer<Migration>() {
            @Override
            public Migration answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Migration) args[0];
            }
        });

        Workload workload = new Workload();
        Mockito.when(workloadService.get(Mockito.any(UUID.class))).thenReturn(workload);

        Migration migration = migrationDataSource.update(migrationId, bind);

        Assertions.assertEquals(migrationId, migration.getId());
    }

    @Test
    public void updateShouldThrowExceptionWhenMigrationNotExists(){
        UUID migrationId = UUID.randomUUID();
        Mockito.when(migrationRepository.existsById(Mockito.any(UUID.class)))
                .thenReturn(false);

        Assertions.assertThrows(InstanceNotFoundException.class, () -> migrationDataSource.update(migrationId, Mockito.any(MigrationBind.class)));
    }

    @Test
    public void getMigrationShouldThrowExceptionWhenItIsNotExists() {
        Mockito.when(migrationRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(InstanceNotFoundException.class, () -> migrationDataSource.get(UUID.randomUUID()));
    }

    @Test
    public void getAllMigrationsShouldThrowExceptionWhenPersistenceIsEmpty() {
        Mockito.when(migrationRepository.findAll())
                .thenReturn(List.of());

        Assertions.assertThrows(InstanceNotFoundException.class, () -> migrationDataSource.getAll());
    }

    @Test
    public void deleteShouldInvokePersistanceLayer() {
        Mockito.doNothing().when(migrationRepository).deleteById(Mockito.any(UUID.class));
        migrationDataSource.delete(UUID.randomUUID());
        Mockito.verify(migrationRepository, Mockito.times(1)).deleteById(Mockito.any(UUID.class));
    }

    @Test
    public void migrationRunShouldInvokeTaskPoolExecutor() {
        Migration migration = Mockito.mock(Migration.class);
        Mockito.when(migration.getState()).thenReturn(State.PENDING);
        Mockito.when(migration.getSourceId()).thenReturn(UUID.randomUUID());

        TargetCloud targetCloud = Mockito.mock(TargetCloud.class);
        Mockito.when(targetCloud.getTargetId())
                .thenReturn(Mockito.any(UUID.class));
        Mockito.when(migration.getTargetCloud()).thenReturn(targetCloud);

        Mockito.when(migrationRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(migration));
        ThreadPoolTaskExecutor executor = Mockito.mock(ThreadPoolTaskExecutor.class);
        Mockito.doNothing().when(executor).execute(Mockito.any(Runnable.class));

        Mockito.when(workloadService.get(Mockito.any(UUID.class)))
                .thenReturn(Mockito.mock(Workload.class));

        migrationDataSource.run(UUID.randomUUID(), executor);

        Mockito.verify(executor, Mockito.times(1)).execute(Mockito.any(Runnable.class));
    }
}
