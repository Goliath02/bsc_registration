package bsc_registration.webInterface.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import bsc_registration.webInterface.dto.CourseDto;
import bsc_registration.infrastructure.repository.CourseRepository;
import bsc_registration.domain.service.CourseService;
import bsc_registration.infrastructure.repository.TrainingPlaceRepository;
import bsc_registration.infrastructure.repository.UserRepository;
import bsc_registration.webInterface.dto.TrainingUnitsDto;
import bsc_registration.domain.entities.BscUser;
import bsc_registration.domain.entities.Course;
import bsc_registration.domain.entities.TrainingPlace;
import bsc_registration.domain.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final DateUtil dateUtil;
    private final CourseRepository courseRepository;
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
