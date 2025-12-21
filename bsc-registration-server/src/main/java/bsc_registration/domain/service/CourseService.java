package bsc_registration.domain.service;

import bsc_registration.domain.entities.*;
import bsc_registration.domain.utils.DateUtil;
import bsc_registration.features.course.repository.CourseRepository;
import bsc_registration.features.course.repository.CourseTypeRepository;
import bsc_registration.features.trainingPlace.repository.TrainingPlaceRepository;
import bsc_registration.features.auth.repository.UserRepository;
import bsc_registration.webInterface.dto.CourseDetails;
import bsc_registration.webInterface.dto.CourseDto;
import bsc_registration.webInterface.dto.CreateCourseRequestDto;
import bsc_registration.webInterface.dto.TrainingUnitsDto;
import jakarta.transaction.Transactional;
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
  private final DateUtil dateUtil;

  public List<CourseDto> getALlCourses() {
    List<Course> all = courseRepository.findAll();

    if (all.isEmpty()) {
      return List.of();
    }

    return all.stream().map(course -> new CourseDto(
      course.getCourseId(),
      course.getCourseName(),
      course.getCourseType().getCourseTypeName(),
      course.getCourseStatus(),
      course.getStartDate(),
      course.getEndDate(),
      course.getNumberOfParticipants(),
      course.getTrainingUnits(),
      course.getCourseOwner().getFullName(),
      course.getPlace().getName()
      )).toList();
  }

	public List<HolidayDateInfo> getAllHolidays() {
		return courseRepository.getAllHolidays();
	}

  @Transactional(rollbackOn = Exception.class)
	public void createCourse(final CreateCourseRequestDto courseDto) {

    final var courseBuilder = Course.builder();

    courseBuilder.courseName(courseDto.getCourseName());

    final CourseType courseType = courseTypeRepository.findById(courseDto.getCourseTypeId()).orElseThrow();
    courseBuilder.courseType(courseType);

    courseBuilder.courseStatus(courseDto.getCourseStatus());

    final TrainingUnitsDto trainingUnitsDto = dateUtil.calculateTrainingDates(courseDto.getStartDateTime()
      .toLocalDate(), courseDto.getTrainingUnits(), getAllHolidays());

    courseBuilder.startDate(courseDto.getStartDateTime().toLocalDate());
    courseBuilder.endDate(trainingUnitsDto.getDates().getLast());
    courseBuilder.numberOfMaxParticipants(courseDto.getNumberOfMaxParticipants());
    courseBuilder.trainingUnits(courseDto.getTrainingUnits());

    final BscUser courseOwner = userRepository.findById(courseDto.getCourseOwnerId()).orElseThrow();

    courseBuilder.courseOwner(courseOwner);

    final TrainingPlace place = placeRepository.findById(courseDto.getPlaceId()).orElseThrow();

    courseBuilder.place(place);

    final Course course = courseBuilder.build();

    List<LocalDate> dates = trainingUnitsDto.getDates();

    List<CourseTraining> trainingUnits = dates.stream().map(d -> {
      CourseTraining ct = new CourseTraining();
      ct.setCourse(course);
      return ct;
    }).toList();

    course.setCourseTrainings(trainingUnits);

    courseRepository.save(course);
  }

    public void updateCourse(final long courseId, final CreateCourseRequestDto courseDto) {

        final Course courseToChange = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course with id " + courseId + " not found"));
        final CourseType courseType = courseTypeRepository.findById(courseDto.getCourseTypeId()).orElseThrow();
        final BscUser courseOwner = userRepository.findById(courseDto.getCourseOwnerId()).orElseThrow();

        courseToChange.setCourseName(courseDto.getCourseName());
        courseToChange.setCourseType(courseType);
        courseToChange.setCourseOwner(courseOwner);
        courseToChange.setTrainingUnits(courseDto.getTrainingUnits());

        final TrainingUnitsDto trainingUnitsDto = dateUtil.calculateTrainingDates(courseDto.getStartDateTime()
                .toLocalDate(), courseDto.getTrainingUnits(), getAllHolidays());

        courseToChange.setStartDate(courseDto.getStartDateTime().toLocalDate());
        courseToChange.setEndDate(trainingUnitsDto.getDates().getLast());

        courseToChange.setStartDate(courseDto.getStartDateTime().toLocalDate());
        courseToChange.setEndDate(courseToChange.getEndDate());
        courseToChange.setNumberOfParticipants(courseToChange.getNumberOfParticipants());

        courseRepository.save(courseToChange);
    }

	public void deleteCourse(final Long courseId) {
		final Optional<Course> byId = courseRepository.findById(courseId);

		if (byId.isPresent()) {
			courseRepository.delete(byId.get());
		} else {
			throw new IllegalArgumentException("Course with id " + courseId + " not found");
		}
	}

  public CourseDetails getCourseDetails(final long courseId) {

    final Course course = courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);

    return new CourseDetails(
      courseId,
      course.getCourseName(),
      course.getCourseType().getCourseTypeId(),
      course.getStartDate(),
      course.getEndDate(),
      course.getNumberOfMaxParticipants(),
      course.getTrainingUnits(),
      course.getCourseStatus(),
      course.getCourseOwner().getUserId(),
      course.getPlace().getId()
      );
  }
}
