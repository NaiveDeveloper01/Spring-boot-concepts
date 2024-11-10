package data_migration.Batch.repository.source;

import data_migration.Batch.entities.sourceEntities.SourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepo extends JpaRepository<SourceEntity,Long> {
}
