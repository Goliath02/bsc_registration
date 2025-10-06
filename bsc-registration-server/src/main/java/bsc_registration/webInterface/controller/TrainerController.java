package bsc_registration.webInterface.controller;

import bsc_registration.domain.service.TrainerService;
import bsc_registration.webInterface.dto.TrainerInfoDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {


    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    public List<TrainerInfoDto> getAllTrainers() {
        return trainerService.getAllTrainers();
    }




}
