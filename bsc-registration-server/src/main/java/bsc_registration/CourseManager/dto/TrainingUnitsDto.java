package bsc_registration.CourseManager.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrainingUnitsDto {

	private List<LocalDate> dates;

	private int numberOfTrainingUnits;

	private List<HolidayDateInfo> holidayDates;

}
