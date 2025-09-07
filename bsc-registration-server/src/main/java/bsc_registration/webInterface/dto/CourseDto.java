package bsc_registration.webInterface.dto;

import bsc_registration.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
	private Long courseId;
	private String courseName;
	private String courseType;
    private CourseStatus courseStatus;
	private LocalDate startDate;
	private LocalDate endDate;
	private int numberOfMaxParticipants;
	private int trainingUnits;
	private String courseOwnerName;
	private String placeName;
}
