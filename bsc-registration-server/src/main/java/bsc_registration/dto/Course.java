package bsc_registration.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courseId;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
    private List<BscUser> user;

    @Column
    private CourseType courseType;

    @Column
    private LocalDate startDate;

    @Column
    private int numberOfParticipants;

    @Column
    private int trainingUnits;
}
