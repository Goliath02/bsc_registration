package bsc_registration.utils;

import bsc_registration.features.info.entity.HolidayDateInfo;
import bsc_registration.webInterface.dto.TrainingUnitsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateUtil {


	public TrainingUnitsDto calculateTrainingDates(LocalDate startDate, final int trainingUnits, final List<HolidayDateInfo> allHolidays) {

		final List<LocalDate> trainingDates = new ArrayList<>();

		final List<HolidayDateInfo> holidayDates = new ArrayList<>();


    int trainingUnitsPlanned = 0;
    LocalDate nextDate = startDate;

    do {
      final HolidayDateInfo holidayDateInfo = isDateBetweenHoliday(nextDate, allHolidays);

      if (holidayDateInfo != null) {
        holidayDates.add(new HolidayDateInfo(holidayDateInfo.getId(), holidayDateInfo.getHolidayName(), holidayDateInfo.getFromDate(),
          holidayDateInfo.getToDate()));
      } else {
        trainingDates.add(nextDate);
        trainingUnitsPlanned++;
      }

      nextDate = nextDate.plusWeeks(1);

    } while (trainingUnits != trainingUnitsPlanned);

    return new TrainingUnitsDto(trainingDates, trainingUnits, holidayDates.stream().distinct().toList());

	}

  private HolidayDateInfo isDateBetweenHoliday(final LocalDate date, final List<HolidayDateInfo> holidayDates) {

		return holidayDates.stream()
      .filter(h -> !date.isBefore(h.getFromDate()) && !date.isAfter(h.getToDate()))
      .findFirst()
      .orElse(null);
	}
}
