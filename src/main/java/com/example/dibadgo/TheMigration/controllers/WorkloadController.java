package com.example.dibadgo.TheMigration.controllers;

import com.example.dibadgo.TheMigration.dataSource.WorkloadDataSource;
import com.example.dibadgo.TheMigration.domain.Workload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * Spring REST controller to manage Workloads
 */
@RestController
@RequestMapping("workload")
public class WorkloadController {

    private WorkloadDataSource workloadService;

    @Autowired
    public void setWorkloadService(WorkloadDataSource workloadService) {
        this.workloadService = workloadService;
    }

    /**
     * List of Workloads
     *
     * @return workloads
     */
    @GetMapping
    public ResponseEntity<List<Workload>> list() {
        return ResponseEntity.ok(workloadService.getAll());
    }

    /**
     * Get a Workload by Id
     *
     * @param id Workload Id
     * @return Workload
     */
    @GetMapping("/{id}")
    public ResponseEntity<Workload> get(@PathVariable UUID id) {
        var workload = workloadService.get(id);
        return ResponseEntity.ok(workload);
    }

    /**
     * Create Workload
     *
     * @param workload Workload model
     * @return Workload
     * @see Workload
     */
    @PostMapping
    public ResponseEntity<Workload> createWorkload(@RequestBody Workload workload) {
        Workload savedWorkload = workloadService.saveWorkload(workload);
        return ResponseEntity.ok(savedWorkload);
    }

    /**
     * Update Workload
     *
     * @param workload Workload model
     * @return Workload
     * @see Workload
     */
    @PutMapping("/{id}")
    public ResponseEntity<Workload> updateWorkload(@PathVariable UUID id, @RequestBody @NotNull Workload workload) {
        workload.setId(id);
        Workload savedWorkload = workloadService.saveWorkload(workload);
        return ResponseEntity.ok(savedWorkload);
    }

    /**
     * Remove Workload
     *
     * @param id Workload Id
     * @return Result message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkload(@PathVariable UUID id) {
        workloadService.delete(id);

        String message = String.format("Workload %s successfully removed", id.toString());
        return ResponseEntity.ok(message);
    }
}
