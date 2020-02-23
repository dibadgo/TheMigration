package com.example.dibadgo.TheMigration.domain;

import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.dataSource.MigrationDataSource;
import com.example.dibadgo.TheMigration.exceptions.LocalMigrationError;

import java.util.Date;

public class MigrationRunner implements Runnable {

    private final static int MIGRATION_TIME_MIN = 5;

    private MigrationDataSource service;
    private Migration migration;

    public MigrationRunner(MigrationDataSource service, Migration migration) {
        this.service = service;
        this.migration = migration;
    }

    @Override
    public void run() {
        try {
            migration.setStartTime(new Date());
            migration.setState(State.RUNNING);
            service.update(migration);

            log("in progress...");
            migration.run(MIGRATION_TIME_MIN);

            migration.setState(State.SUCCESS);
            migration.setEndTime(new Date());
            service.update(migration);
            log("Migration completed successfully");
        } catch (LocalMigrationError | InterruptedException migrationError) {
            migration.setState(State.ERROR);
            migration.setErrorMessage(migrationError.getMessage());
            migration.setEndTime(new Date());
            service.update(migration);

            String errorMessage = String.format(
                    "Error while running migration: %s",
                    migrationError.getMessage()
            );
            log(errorMessage);
        }
    }

    private void log(String message) {
        String errorMessage = String.format(
                "Migration %s: %s",
                migration.getId().toString(),
                message
        );
        System.out.println(errorMessage);
    }
}
