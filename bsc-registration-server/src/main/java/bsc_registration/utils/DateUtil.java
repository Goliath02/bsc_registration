package bsc_registration.utils;

import bsc_registration.CourseManager.CourseService;
import bsc_registration.CourseManager.dto.HolidayDateInfo;
import bsc_registration.CourseManager.dto.TrainingUnitsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DateUtil {

	private final CourseService courseService;

	public TrainingUnitsDto calculateTrainingDates(final LocalDate startDate, final int trainingUnits) {

		final List<LocalDate> trainingDates = new ArrayList<>();

		final List<HolidayDateInfo> holidayDates = new ArrayList<>();

		trainingDates.add(startDate);

		for (int i = 0; i < trainingUnits; i++) {

			final LocalDate nextTrainingUnit = startDate.plusWeeks(1);

			final Optional<HolidayDateInfo> dateInHoliday = courseService.getDateInHoliday(nextTrainingUnit);

			if (dateInHoliday.isPresent()) {
				final HolidayDateInfo holidayDateInfo = dateInHoliday.get();
				holidayDates.add(new HolidayDateInfo(null, holidayDateInfo.getHolidayName(), holidayDateInfo.getFromDate(), holidayDateInfo.getToDate()));
			} else {
				trainingDates.add(nextTrainingUnit);
			}
		}
		return new TrainingUnitsDto(trainingDates, trainingUnits, holidayDates);
	}
}
