package data_migration.Batch.entities.sourceEntities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "actor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SourceEntity {
  @Id
  @Column(name = "actor_id")
  private Long actorId;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "last_update")
  private Date lastUpdated;
}
