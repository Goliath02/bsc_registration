package bsc_registration.Controller;

import bsc_registration.CourseManager.CourseService;
import bsc_registration.CourseManager.dto.HolidayDateInfo;
import bsc_registration.CourseManager.dto.TrainingUnitsDto;
import bsc_registration.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/course/")
@RequiredArgsConstructor
public class CourseController {

	private final CourseService courseService;
	private final DateUtil dateUtil;

	@GetMapping("holidayDateInfo")
	public List<HolidayDateInfo> getHolidayDateInfo(@RequestParam final LocalDate startDate, @RequestParam final int trainingUnits) {

		TrainingUnitsDto trainingUnitsDto = dateUtil.calculateTrainingDates(startDate, trainingUnits);

		courseService.getHolidayInfoForCourse(startDate, endDate);

		return courseService.getHolidayInfoForCourse(startDate, endDate);
	}

}
