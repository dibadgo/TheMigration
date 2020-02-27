package com.example.dibadgo.TheMigration.controllers;

import com.example.dibadgo.TheMigration.dataSource.WorkloadDataSource;
import com.example.dibadgo.TheMigration.domain.Workload;
import com.example.dibadgo.TheMigration.exceptions.WorkloadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Workload> get(@PathVariable UUID id) {
        var workload = workloadService.get(id);
        return ResponseEntity.ok(workload);
    }

    /**
     * Create or update Workload
     *
     * @param workload Workload model
     * @return Workload
     * @see Workload
     */
    @PostMapping("/save")
    public ResponseEntity<Workload> saveWorkload(@RequestBody Workload workload) {
        try {
            Workload savedWorkload = workloadService.saveWorkload(workload);
            return ResponseEntity.ok(savedWorkload);
        } catch (WorkloadException workloadException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    workloadException.getMessage(),
                    workloadException // It is wrong way to deliver the internal exception to the clients. Only debug.
            );
        }
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
        return ResponseEntity.ok("Workload successfully removed");
    }
}
