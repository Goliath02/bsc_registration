package bsc_registration.Controller;

import bsc_registration.CourseManager.CourseService;
import bsc_registration.CourseManager.dto.TrainingUnitsDto;
import bsc_registration.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
