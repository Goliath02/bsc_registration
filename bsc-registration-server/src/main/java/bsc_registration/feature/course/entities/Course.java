package bsc_registration.feature.course.entities;

import bsc_registration.feature.trainingPlace.TrainingPlace;
import bsc_registration.feature.auth.entities.BscUser;
import bsc_registration.feature.course.dto.CourseType;
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

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
	private List<BscUser> user;

	@Column
	private String courseName;

	@Column
	private CourseType courseType;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

	@Column
	private int numberOfParticipants;

	@Column
	private int trainingUnits;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private BscUser courseOwner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private TrainingPlace place;
}
