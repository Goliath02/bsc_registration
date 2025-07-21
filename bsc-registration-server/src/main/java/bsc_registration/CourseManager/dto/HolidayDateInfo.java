package bsc_registration.CourseManager.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "holiday_dates")
public class HolidayDateInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@Column
	private String holidayName;

	@Column
	private LocalDate fromDate;

	@Column
	private LocalDate toDate;
}
