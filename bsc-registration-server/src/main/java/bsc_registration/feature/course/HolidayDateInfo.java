package bsc_registration.feature.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@Table(name = "holiday_dates")
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
