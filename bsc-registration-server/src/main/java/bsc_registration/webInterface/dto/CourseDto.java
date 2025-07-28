package bsc_registration.webInterface.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseDto {
    private Long courseId;
    private String courseName;
    private CourseType courseType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfParticipants;
    private int trainingUnits;
    private long courseOwnerId;
    private long placeId;
}
