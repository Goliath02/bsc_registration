package bsc_registration.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "Courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COURSE_TYPE")
    private CourseType courseType;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "NUMBER_OF_PARTICIPANTS")
    private int numberOfParticipants;

    @Column(name = "TRAINING_UNITS")
    private int trainingUnits;
}
