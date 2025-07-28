package bsc_registration.domain.utils;

import bsc_registration.domain.service.CourseService;
import bsc_registration.domain.entities.HolidayDateInfo;
import bsc_registration.webInterface.dto.TrainingUnitsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateUtil {

	private final CourseService courseService;

	public TrainingUnitsDto calculateTrainingDates(LocalDate startDate, final int trainingUnits) {

		final List<LocalDate> trainingDates = new ArrayList<>();

		final List<HolidayDateInfo> holidayDates = new ArrayList<>();

		trainingDates.add(startDate);

		List<HolidayDateInfo> allHolidays = courseService.getAllHolidays();

		for (int i = 0; i < trainingUnits; i++) {

			final HolidayDateInfo holidayDateInfo = getDateBetweenHoliday(startDate, allHolidays);

			if (getDateBetweenHoliday(startDate, allHolidays) != null) {
				holidayDates.add(new HolidayDateInfo(null, holidayDateInfo.getHolidayName(), holidayDateInfo.getFromDate(), holidayDateInfo.getToDate()));
			} else {
				trainingDates.add(startDate);
			}

			startDate = startDate.plusWeeks(1);
		}
		return new TrainingUnitsDto(trainingDates, trainingUnits, holidayDates.stream().distinct().toList());
	}

	private HolidayDateInfo getDateBetweenHoliday(final LocalDate date, final List<HolidayDateInfo> holidayDates) {

		return holidayDates.stream().filter(h -> h.getFromDate().isBefore(date) && h.getToDate().isAfter(date)).findFirst().orElse(null);
	}
}
