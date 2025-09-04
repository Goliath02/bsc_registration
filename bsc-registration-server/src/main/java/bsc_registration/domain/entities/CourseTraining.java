package bsc_registration.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "course_training")
@Table(name = "course_training")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTraining {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private long courseTrainingId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id")
  private Course course;

  @Column
  private Long numberOfParticipants;

  @ManyToMany
  @JoinTable(
    name = "course_training_participants",
    joinColumns = @JoinColumn(name = "course_training_id"),
    inverseJoinColumns = @JoinColumn(name = "member_id")
  )
  private Set<BscMember> participants = new HashSet<>();

}
