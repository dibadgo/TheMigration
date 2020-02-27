package com.example.dibadgo.TheMigration.domain;

import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.dataSource.MigrationDataSource;
import com.example.dibadgo.TheMigration.exceptions.LocalMigrationError;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Class wrap the process of migration in background
 *
 * @author Artyom
 */
public class MigrationRunner implements Runnable {

    /**
     * Time of delay migration in minutes
     */
    private final static int MIGRATION_TIME_MIN = 5;
    /**
     * Migration DataSource
     */
    private MigrationDataSource service;
    /**
     * Instance of migration
     */
    private Migration migration;

    /**
     * Constructor
     *
     * @param service   Migration DataSource
     * @param migration Instance of migration to run
     * @see MigrationRunner#service
     * @see MigrationRunner#migration
     */
    public MigrationRunner(@NotNull MigrationDataSource service, @NotNull  Migration migration) {
        this.service = service;
        this.migration = migration;
    }

    /**
     * Will run the migration in background thread
     */
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
        } catch (LocalMigrationError migrationError) {
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

    /**
     * Method for logging the migration by pattern "Migration %Migration id%: message "
     *
     * @param message text to logging
     */
    private void log(String message) {
        String errorMessage = String.format(
                "Migration %s: %s",
                migration.getId().toString(),
                message
        );
        System.out.println(errorMessage);
    }
}
