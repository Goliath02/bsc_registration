package bsc_registration.webInterface.controller;

import bsc_registration.domain.entities.BscUser;
import bsc_registration.domain.entities.TrainingPlace;
import bsc_registration.domain.service.InfoService;
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
	public List<BscUser> getAllTrainer() {
		return infoService.getAllTrainers();
	}

	@GetMapping("/places")
	public List<TrainingPlace> getAllTrainingPlaces() {
		return infoService.getAllTrainingPlaces();
	}

}
