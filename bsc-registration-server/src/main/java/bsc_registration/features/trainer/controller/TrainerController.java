package bsc_registration.features.trainer.controller;

import bsc_registration.features.trainer.service.TrainerService;
import bsc_registration.webInterface.dto.TrainerDto;
import bsc_registration.webInterface.dto.TrainerInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {


    private final TrainerService trainerService;

    @GetMapping
    public List<TrainerInfoDto> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    @PostMapping
    public ResponseEntity<String> createTrainer(@RequestBody final TrainerDto trainerDto) {

        try {
            trainerService.createTrainer(trainerDto);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("EMAIL_ALREADY_EXISTS");
        }

        return ResponseEntity.ok().body("SUCCESS");
    }

    @PutMapping
    public void updateTrainerName(@RequestParam final long trainerId, @RequestParam final String trainerName) {
        trainerService.updateTrainerName(trainerId, trainerName);
    }

    @DeleteMapping
    public void deleteTrainer(@RequestParam final long trainerId) {
        trainerService.deleteTrainer(trainerId);
    }

}
