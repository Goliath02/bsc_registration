package bsc_registration.features.course.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "CourseType")
@Table(name = "course_type")
@Data
public class CourseType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private long courseTypeId;

  @Column
  private String courseTypeName;


}
