package com.example.dibadgo.TheMigration.dataSource;

import com.example.dibadgo.TheMigration.domain.Migration;
import com.example.dibadgo.TheMigration.domain.MigrationBind;

import java.util.List;
import java.util.UUID;

public interface MigrationDataSource {

    Migration create(MigrationBind migrationBind);

    Migration update(UUID migrationId, MigrationBind migrationBind);

    Migration update(Migration migration);

    void delete(UUID id);

    Migration get(UUID migrationId);

    List<Migration> getAll();
}
