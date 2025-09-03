package bsc_registration.webInterface.controller;

import bsc_registration.domain.service.CourseService;
import bsc_registration.domain.utils.DateUtil;
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
  public ResponseEntity createCourse(@RequestBody final CourseDto courseDto) {

    courseService.createCourse(courseDto);

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
