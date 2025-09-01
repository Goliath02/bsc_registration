package bsc_registration.webInterface.controller;

import bsc_registration.domain.entities.BscUser;
import bsc_registration.domain.entities.Course;
import bsc_registration.domain.entities.TrainingPlace;
import bsc_registration.domain.service.CourseService;
import bsc_registration.domain.utils.DateUtil;
import bsc_registration.infrastructure.repository.TrainingPlaceRepository;
import bsc_registration.infrastructure.repository.UserRepository;
import bsc_registration.webInterface.dto.CourseDto;
import bsc_registration.webInterface.dto.TrainingUnitsDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;
  private final DateUtil dateUtil;
  private final UserRepository userRepository;
  private final TrainingPlaceRepository placeRepository;

  @GetMapping("/holidayDateInfo")
  public TrainingUnitsDto getHolidayDateInfo(@RequestParam OffsetDateTime startDate, @RequestParam final int trainingUnits) {

    return dateUtil.calculateTrainingDates(startDate.toLocalDate(), trainingUnits);
  }


  @GetMapping("/all")
  public List<CourseDto> getCourses() {
    return courseService.getALlCourses();
  }

  @PostMapping("/create")
  @Transactional(rollbackOn = Exception.class)
  public ResponseEntity createCourse(final CourseDto courseDto) {

    final var courseBuilder = Course.builder();

    courseBuilder.courseName(courseDto.getCourseName());
    courseBuilder.courseType(courseDto.getCourseType());
    courseBuilder.startDate(courseDto.getStartDate());
    courseBuilder.endDate(courseDto.getEndDate());
    courseBuilder.numberOfParticipants(courseDto.getNumberOfParticipants());
    courseBuilder.trainingUnits(courseDto.getTrainingUnits());

    final BscUser courseOwner = userRepository.findById(courseDto.getCourseOwnerId()).orElseThrow();

    courseBuilder.courseOwner(courseOwner);

    final TrainingPlace place = placeRepository.findById(courseDto.getPlaceId()).orElseThrow();

    courseBuilder.place(place);

    courseService.createCourse();

    return ResponseEntity.ok().build();
  }

  @PutMapping("/update")
  @Transactional(rollbackOn = Exception.class)
  public ResponseEntity updateCourse(final CourseDto courseDto) {
    courseService.updateCourse(courseDto);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/delete/{courseId}")
  @Transactional(rollbackOn = Exception.class)
  public ResponseEntity deleteCourse(@RequestParam final Long courseId) {
    courseService.deleteCourse(courseId);

    return ResponseEntity.ok().build();
  }
}
