package bsc_registration.feature.trainer.controller;

import bsc_registration.feature.auth.entities.BscUser;
import bsc_registration.common.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerController {

    private final InfoService infoService;

    @GetMapping("/trainers")
    public List<BscUser> getAllTrainer() {
        return infoService.getAllTrainers();
    }

}
