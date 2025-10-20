package bsc_registration.webInterface.controller;

import bsc_registration.domain.entities.CourseType;
import bsc_registration.domain.entities.TrainingPlace;
import bsc_registration.domain.service.InfoService;
import bsc_registration.webInterface.dto.TrainerInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/info")
@RequiredArgsConstructor
public class InfoController {

	private final InfoService infoService;

	@GetMapping("/trainers")
	public List<TrainerInfoDto> getAllTrainer() {
		return infoService.getAllTrainers();
	}

	@GetMapping("/places")
    @Deprecated
	public List<TrainingPlace> getAllTrainingPlaces() {
		return infoService.getAllTrainingPlaces();
	}

  @GetMapping("/courseTypes")
  public List<CourseType> getAllCourseTypes() {
    return infoService.getAllCourseTypes();
  }
}
