package bsc_registration.Controller;

import bsc_registration.CourseManager.CourseRepository;
import bsc_registration.CourseManager.CourseService;
import bsc_registration.CourseManager.dto.CourseDto;
import bsc_registration.CourseManager.dto.TrainingUnitsDto;
import bsc_registration.Login.Repository.TrainingPlaceRepository;
import bsc_registration.Login.Repository.UserRepository;
import bsc_registration.dto.BscUser;
import bsc_registration.dto.Course;
import bsc_registration.dto.TrainingPlace;
import bsc_registration.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Optional;

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
