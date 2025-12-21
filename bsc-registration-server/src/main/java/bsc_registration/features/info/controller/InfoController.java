package bsc_registration.features.info.controller;

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
    @Deprecated
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
