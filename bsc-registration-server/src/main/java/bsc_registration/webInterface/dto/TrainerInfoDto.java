package bsc_registration.webInterface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TrainerInfoDto {

  private Long trainerId;
  private String trainerName;
}
