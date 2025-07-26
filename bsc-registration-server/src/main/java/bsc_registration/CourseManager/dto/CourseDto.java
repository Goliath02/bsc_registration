package bsc_registration.CourseManager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseDto {
    private Long courseId;
    private String courseName;
    private int courseTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfParticipants;
    private int trainingUnits;
    private int courseOwnerId;
    private int placeId;
}
