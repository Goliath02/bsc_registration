package bsc_registration.webInterface.dto;

import bsc_registration.enums.CourseStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCourseRequestDto {

  private String courseName;
  private LocalDateTime startDateTime;
  private Integer trainingUnits;
  private long courseOwnerId;
  private long placeId;
  private Integer numberOfMaxParticipants;
  private long courseTypeId;
  private CourseStatus courseStatus;
}