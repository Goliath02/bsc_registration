package bsc_registration.features.trainingPlace.controller;

import bsc_registration.domain.entities.TrainingPlace;
import bsc_registration.domain.service.TrainingPlaceService;
import bsc_registration.webInterface.dto.TrainingPlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final TrainingPlaceService trainingPlaceService;

    @GetMapping("/all")
    public List<TrainingPlace> getAllPlaces() {
        return trainingPlaceService.getAllTrainingPlaces();
    }

    @PostMapping()
    public ResponseEntity<Object> savePlace(@RequestBody final TrainingPlaceDto placeDto) {

        trainingPlaceService.saveTrainingPlace(placeDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Object> updatePlace(@RequestParam final long id, @RequestBody final TrainingPlaceDto placeDto) {

        trainingPlaceService.updateTrainingPlace(id, placeDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Object> deletePlace(@RequestParam final long id) {

        trainingPlaceService.deleteTrainingPlace(id);

        return ResponseEntity.ok().build();
    }

}
