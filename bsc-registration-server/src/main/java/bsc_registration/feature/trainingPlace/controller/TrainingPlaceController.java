package bsc_registration.feature.trainingPlace.controller;

import bsc_registration.feature.trainingPlace.TrainingPlace;
import bsc_registration.common.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlaceController {

    private final InfoService infoService;

    @GetMapping("/places")
    public List<TrainingPlace> getAllTrainingPlaces() {
        return infoService.getAllTrainingPlaces();
    }


}
