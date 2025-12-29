package bsc_registration.webInterface.dto;

import bsc_registration.features.info.entity.HolidayDateInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class TrainingUnitsDto {

	private List<LocalDate> dates;

	private int numberOfTrainingUnits;

	private List<HolidayDateInfo> holidayDates;

}
