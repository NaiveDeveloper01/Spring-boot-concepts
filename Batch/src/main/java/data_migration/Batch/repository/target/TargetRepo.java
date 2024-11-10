package data_migration.Batch.repository.target;

import data_migration.Batch.entities.targetEntities.TargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepo extends JpaRepository<TargetEntity,Long> {
}
