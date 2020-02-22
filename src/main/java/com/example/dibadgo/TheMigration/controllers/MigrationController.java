package com.example.dibadgo.TheMigration.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("migration")
public class MigrationController {

//    MigrationRepository migrationRepository;

//    @Autowired
//    public Migration GetMigration(UUID id) {
//        return migrationRepository.findById(id).get();
//    }

    @GetMapping
    public String list() {
        return "list";
    }
}
