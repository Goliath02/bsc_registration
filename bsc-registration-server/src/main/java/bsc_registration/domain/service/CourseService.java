package bsc_registration.domain.service;

import bsc_registration.domain.entities.*;
import bsc_registration.infrastructure.repository.CourseRepository;
import bsc_registration.infrastructure.repository.CourseTypeRepository;
import bsc_registration.infrastructure.repository.TrainingPlaceRepository;
import bsc_registration.infrastructure.repository.UserRepository;
import bsc_registration.webInterface.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
  private final UserRepository userRepository;
  private final TrainingPlaceRepository placeRepository;
  private final CourseTypeRepository courseTypeRepository;

  public List<CourseDto> getALlCourses() {
    List<Course> all = courseRepository.findAll();

    if (all.isEmpty()) {
      return List.of();
    }

    return all.stream().map(course -> new CourseDto(
      course.getCourseId(),
      course.getCourseName(),
      course.getCourseType().getCourseTypeId(),
      course.getStartDate(),
      course.getEndDate(),
      course.getNumberOfParticipants(),
      course.getTrainingUnits(),
      course.getCourseOwner().getUserId(),
      course.getPlace().getId()
      )).toList();
  }

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

	public void createCourse(final CourseDto courseDto) {

    final var courseBuilder = Course.builder();

    courseBuilder.courseName(courseDto.getCourseName());

    final CourseType courseType = courseTypeRepository.findById(courseDto.getCourseTypeId()).orElseThrow();
    courseBuilder.courseType(courseType);

    courseBuilder.startDate(courseDto.getStartDate());
    courseBuilder.endDate(courseDto.getEndDate());
    courseBuilder.numberOfParticipants(courseDto.getNumberOfMaxParticipants());
    courseBuilder.trainingUnits(courseDto.getTrainingUnits());

    final BscUser courseOwner = userRepository.findById(courseDto.getCourseOwnerId()).orElseThrow();

    courseBuilder.courseOwner(courseOwner);

    final TrainingPlace place = placeRepository.findById(courseDto.getPlaceId()).orElseThrow();

    courseBuilder.place(place);

		courseRepository.save(courseBuilder.build());
	}

	public void updateCourse(final CourseDto courseDto) {

		final Optional<Course> byId = courseRepository.findById(courseDto.getCourseId());

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
		final Optional<Course> byId = courseRepository.findById(courseId);

		if (byId.isPresent()) {
			courseRepository.delete(byId.get());
		} else {
			throw new IllegalArgumentException("Course with id " + courseId + " not found");
		}
	}
}
