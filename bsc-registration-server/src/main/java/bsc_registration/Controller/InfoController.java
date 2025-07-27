package bsc_registration.Controller;

import bsc_registration.Service.InfoManager;
import bsc_registration.dto.BscUser;
import bsc_registration.dto.TrainingPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/info")
@RequiredArgsConstructor
public class InfoController {

	private final InfoManager infoManager;

	@GetMapping("/trainers")
	public List<BscUser> getAllTrainer() {
		return infoManager.getAllTrainers();
	}

	@GetMapping("/places")
	public List<TrainingPlace> getAllTrainingPlaces() {
		return infoManager.getAllTrainingPlaces();
	}

}
