package bsc_registration.CourseManager;

import bsc_registration.CourseManager.dto.HolidayDateInfo;
import bsc_registration.dto.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {


	@Query("SELECT c FROM Course c")
	List<Course> getAllCourses();

	@Query("""
    SELECT h FROM HolidayDateInfo h
    WHERE h.fromDate <= :endDate
    AND h.toDate >= :startDate
""")
List<HolidayDateInfo> getHolidayDateInfoBetweenDates(
    final LocalDate startDate,
    final LocalDate endDate
);

	@Query("SELECT h FROM HolidayDateInfo h")
	List<HolidayDateInfo> getAllHolidays();

	@Query("SELECT CASE WHEN :date BETWEEN h.fromDate AND h.toDate THEN TRUE ELSE FALSE END FROM HolidayDateInfo h")
	boolean isDateInHoliday(final LocalDate date);

	@Query("SELECT h FROM HolidayDateInfo h WHERE :trainingDate BETWEEN h.fromDate AND h.toDate")
	Optional<HolidayDateInfo> getDateInHoliday(@Param("trainingDate") LocalDate trainingDate);
}