package bsc_registration.webInterface.dto;

import bsc_registration.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CourseDetails {

  private Long courseId;

  private String courseName;

  private long courseTypeId;

  private LocalDate startDate;
  private LocalDate endDate;

  private int numberOfMaxParticipants;
  private int trainingUnits;

  private CourseStatus courseStatus;

  private long courseOwnerId;

  private long placeId;

}
