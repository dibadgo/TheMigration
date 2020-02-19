package com.example.dibadgo.TheMigration.persistent.repositoryes;


import com.example.dibadgo.TheMigration.model.Migration;
import com.example.dibadgo.TheMigration.persistent.CassandraConnector;

public class MigrationRepository extends BaseRepository<Migration> {

    public MigrationRepository(CassandraConnector connector) {
        super(connector);
    }
}
