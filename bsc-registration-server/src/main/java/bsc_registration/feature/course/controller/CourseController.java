package bsc_registration.feature.course.controller;

import bsc_registration.feature.auth.entities.BscUser;
import bsc_registration.feature.auth.repository.UserRepository;
import bsc_registration.feature.course.CourseService;
import bsc_registration.feature.course.dto.CourseDto;
import bsc_registration.feature.course.dto.TrainingUnitsDto;
import bsc_registration.feature.course.entities.Course;
import bsc_registration.feature.trainingPlace.TrainingPlace;
import bsc_registration.feature.trainingPlace.repository.TrainingPlaceRepository;
import bsc_registration.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

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

    @PostMapping("/create")
    public void createCourse(final CourseDto courseDto) {

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
    }

    @PutMapping("/update")
    public void updateCourse(final CourseDto courseDto) {
        courseService.updateCourse(courseDto);
    }

    @DeleteMapping("/delete/{courseId}")
    public void deleteCourse(@RequestParam final Long courseId) {
        courseService.deleteCourse(courseId);
    }
}
