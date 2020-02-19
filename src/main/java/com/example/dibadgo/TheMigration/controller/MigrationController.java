package com.example.dibadgo.TheMigration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class MigrationController {

    @GetMapping
    public String list() {
        return "list";
    }
}
