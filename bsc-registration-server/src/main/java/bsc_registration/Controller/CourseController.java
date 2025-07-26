package bsc_registration.Controller;

import bsc_registration.CourseManager.CourseService;
import bsc_registration.CourseManager.dto.CourseDto;
import bsc_registration.CourseManager.dto.TrainingUnitsDto;
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

    @GetMapping("/holidayDateInfo")
    public TrainingUnitsDto getHolidayDateInfo(@RequestParam OffsetDateTime startDate, @RequestParam final int trainingUnits) {

        return dateUtil.calculateTrainingDates(startDate.toLocalDate(), trainingUnits);
    }

    @PostMapping("/create")
    public void createCourse(final CourseDto courseDto) {

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
