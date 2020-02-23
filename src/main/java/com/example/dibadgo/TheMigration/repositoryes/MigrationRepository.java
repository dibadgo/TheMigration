package com.example.dibadgo.TheMigration.repositoryes;

import com.example.dibadgo.TheMigration.domain.Migration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MigrationRepository extends CrudRepository<Migration, UUID> {
}
