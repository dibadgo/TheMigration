package com.example.dibadgo.TheMigration.controllers;

import com.example.dibadgo.TheMigration.base.State;
import com.example.dibadgo.TheMigration.dataSource.MigrationDataSource;
import com.example.dibadgo.TheMigration.domain.Migration;
import com.example.dibadgo.TheMigration.domain.MigrationBind;
import com.example.dibadgo.TheMigration.domain.MigrationRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * Spring REST controller for manage the migration process
 */
@RestController
@RequestMapping("migration")
public class MigrationController {

    private MigrationDataSource migrationDataSource;
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public void setMigrationService(MigrationDataSource migrationDataSource) {
        this.migrationDataSource = migrationDataSource;
    }

    @Autowired
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * Create a migration from bind model and save to DB
     *
     * @param bind Migration bind model
     * @return Migration model
     * @see MigrationBind
     */
    @PostMapping("/create")
    public ResponseEntity<Migration> createMigration(@RequestBody MigrationBind bind) {
        try {
            return ResponseEntity.ok(migrationDataSource.create(bind));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e // It is wrong way delivers the internal exception to the clients. Only debug.
            );
        }
    }

    /**
     * Search a migration by Id
     *
     * @param id Migration id
     * @return Migration model or HTTP 404 if not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Migration> get(@PathVariable UUID id) {
        var migration = migrationDataSource.get(id);
        return ResponseEntity.ok(migration);
    }

    /**
     * Run the migration process in background thread
     *
     * @param id Id of the migration
     * @return Message
     */
    @GetMapping("/run/{id}")
    public ResponseEntity<String> run(@PathVariable UUID id) {
        Migration migration = migrationDataSource.get(id);
        if (migration.getState() != State.PENDING) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot start the migration when state is not pending"
            );
        }

        MigrationRunner migrationRunner = new MigrationRunner(migrationDataSource, migration);
        taskExecutor.execute(migrationRunner);

        return ResponseEntity.ok("The migration process started. " +
                "Check out migration/id method to observe on the status");
    }

    /**
     * Returns list of migrations
     *
     * @return Migrations
     */
    @GetMapping()
    public ResponseEntity<List<Migration>> list() {
        return ResponseEntity.ok(migrationDataSource.getAll());
    }

    /**
     * Update a migration from MigrationBind
     *
     * @param id   Migration Id
     * @param bind Bind model which will transform to migrations
     * @return
     * @see MigrationBind
     */
    @PutMapping("/{id}")
    public ResponseEntity<Migration> update(@PathVariable UUID id, @RequestBody MigrationBind bind) {
        return ResponseEntity.ok(migrationDataSource.update(id, bind));
    }

    /**
     * Delete a migration by id
     *
     * @param id Delete the migration
     * @return Operation status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable UUID id) {
        migrationDataSource.delete(id);
        return ResponseEntity.ok("Migration successfully removed");
    }
}
