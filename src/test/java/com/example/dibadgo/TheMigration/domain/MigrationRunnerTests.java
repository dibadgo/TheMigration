package com.example.dibadgo.TheMigration.domain;

import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.dataSource.MigrationDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

public class MigrationRunnerTests {

    private Migration migration;
    private MigrationDataSource migrationDataSource;

    @BeforeEach
    public void setUp() {
        migrationDataSource = Mockito.mock(MigrationDataSource.class);

        migration = Mockito.mock(Migration.class);
        Mockito.when(migration.getId()).thenReturn(UUID.randomUUID());
        Mockito.doNothing().when(migration).run(Mockito.anyInt());
        Mockito.when(migration.getState()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(migration).setState(Mockito.any(State.class));
    }

    @Test
    public void updatePersistsCalled() {
        MigrationRunner runner = new MigrationRunner(migrationDataSource, migration);
        runner.run();

        Mockito.verify(migrationDataSource, Mockito.times(2)).update(Mockito.any(Migration.class));
    }

    @Test
    public void makeSuccessfulMigration() {
        MigrationRunner runner = new MigrationRunner(migrationDataSource, migration);
        runner.run();

        Assertions.assertEquals(State.SUCCESS, migration.getState());
    }

    @Test
    public void makeFailedMigration() {
        Mockito.doThrow(RuntimeException.class)
                .when(migration)
                .run(Mockito.anyInt());

        MigrationRunner runner = new MigrationRunner(migrationDataSource, migration);
        runner.run();

        Assertions.assertEquals(State.ERROR, migration.getState());
    }
}
