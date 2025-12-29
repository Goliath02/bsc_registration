package bsc_registration.features.info.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@Table(name = "holiday_dates")
@EqualsAndHashCode(of = {"holidayName", "fromDate", "toDate"})
@NoArgsConstructor
public class HolidayDateInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column
	private String holidayName;

	@Column
	private LocalDate fromDate;

	@Column
	private LocalDate toDate;
}
