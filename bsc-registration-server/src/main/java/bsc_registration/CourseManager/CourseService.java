package bsc_registration.CourseManager;

import bsc_registration.CourseManager.dto.HolidayDateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;

	public List<HolidayDateInfo> getHolidayInfoForCourse(final LocalDate startDate, final LocalDate endDate) {
		return courseRepository.getHolidayDateInfoBetweenDates(startDate, endDate);
	}

	public List<HolidayDateInfo> getAllHolidays() {
		return courseRepository.getAllHolidays();
	}

	public boolean isDateBetweenHoliday(final LocalDate date) {
		return courseRepository.isDateInHoliday(date);
	}
}
