package bsc_registration.utils;

import bsc_registration.CourseManager.CourseService;
import bsc_registration.CourseManager.dto.HolidayDateInfo;
import bsc_registration.CourseManager.dto.TrainingUnitsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateUtil {

	private final CourseService courseService;

	public TrainingUnitsDto calculateTrainingDates(final LocalDate startDate, final int trainingUnits) {

		final List<HolidayDateInfo> holidayInfoForCourse = courseService.getAllHolidays();

		for (int i = 0; i < trainingUnits; i++) {

			final LocalDate nextTrainingUnit = startDate.plusWeeks(1);

			if (courseService.isDateBetweenHoliday(nextTrainingUnit)) {

			}

		}
//TODO finish
		return null;
	}
}
