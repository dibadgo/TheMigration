package com.example.dibadgo.TheMigration.controllers;

import com.example.dibadgo.TheMigration.dataSource.WorkloadDataSource;
import com.example.dibadgo.TheMigration.domain.Workload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("workload")
public class WorkloadController {

    private WorkloadDataSource workloadService;

    @Autowired
    public void setWorkloadService(WorkloadDataSource workloadService) {
        this.workloadService = workloadService;
    }

    @GetMapping
    public ResponseEntity<List<Workload>> list() {
        return ResponseEntity.ok(workloadService.getAll());
    }

    @PutMapping("/save")
    public ResponseEntity<Workload> saveWorkload(@RequestBody Workload workload) {
        Workload savedWorkload = workloadService.saveWorkload(workload);
        return ResponseEntity.ok(savedWorkload);
    }
}
