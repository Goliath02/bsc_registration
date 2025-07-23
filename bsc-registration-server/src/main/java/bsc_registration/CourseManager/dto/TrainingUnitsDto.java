package bsc_registration.CourseManager.dto;

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
