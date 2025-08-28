package bsc_registration.domain.entities;

import bsc_registration.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "course")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long courseId;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "course_members",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "member_id")
  )
  private List<BscMember> members;

  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CourseTraining> courseTrainings;

	@Column
	private String courseName;

	@ManyToOne()
  @JoinColumn(name = "course_type_id")
	private CourseType courseType;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

	@Column
	private int numberOfParticipants;

  @Column
  private int numberOfMaxParticipants;

	@Column
	private int trainingUnits;

  @Column(length = 25)
  @Enumerated(EnumType.STRING)
  private CourseStatus courseStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private BscUser courseOwner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private TrainingPlace place;
}
