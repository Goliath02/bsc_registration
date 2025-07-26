package bsc_registration.CourseManager;

import bsc_registration.CourseManager.dto.CourseDto;
import bsc_registration.CourseManager.dto.HolidayDateInfo;
import bsc_registration.dto.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

	public Optional<HolidayDateInfo> getDateInHoliday(final LocalDate date) {
		return courseRepository.getDateInHoliday(date);
	}

	public void createCourse() {

		final Course.CourseBuilder courseBuilder = Course.builder();

		courseRepository.save(new Course());
	}

	public void updateCourse(final CourseDto courseDto) {

		Optional<Course> byId = courseRepository.findById(courseDto.getCourseId());

		if (byId.isPresent()) {
			final Course course = byId.get();


			//TODO finish (should fetch courseOwner by id +  set type + placeById
			course.setCourseName(course.getCourseName());
			course.setCourseType(course.getCourseType());
			course.setCourseOwner(course.getCourseOwner());
			course.setTrainingUnits(course.getTrainingUnits());
			course.setStartDate(course.getStartDate());
			course.setEndDate(course.getEndDate());
			course.setNumberOfParticipants(course.getNumberOfParticipants());

			courseRepository.save(course);
		}

	}

	public void deleteCourse(final Long courseId) {
		Optional<Course> byId = courseRepository.findById(courseId);

		if (byId.isPresent()) {
			courseRepository.delete(byId.get());
		} else {
			throw new IllegalArgumentException("Course with id " + courseId + " not found");
		}
	}
}
