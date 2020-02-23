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

    @PostMapping("/create")
    public ResponseEntity<Migration> createMigration(@RequestBody MigrationBind bind) {
        try{
            return ResponseEntity.ok(migrationDataSource.create(bind));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e // It is wrong way delivers the internal exception to the clients. Only debug.
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Migration> get(@PathVariable UUID id) {
        var migration = migrationDataSource.get(id);
        return ResponseEntity.ok(migration);
    }

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

    @GetMapping()
    public ResponseEntity<List<Migration>> list() {
        return ResponseEntity.ok(migrationDataSource.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Migration> update(@PathVariable UUID id, @RequestBody MigrationBind bind) {
        return ResponseEntity.ok(migrationDataSource.update(id, bind));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable UUID id) {
        migrationDataSource.delete(id);
        return ResponseEntity.ok("Migration successfully removed");
    }
}
