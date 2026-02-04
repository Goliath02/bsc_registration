package bsc_registration.common;

import bsc_registration.feature.auth.dto.AuthorityType;
import bsc_registration.feature.auth.entities.BscUser;
import bsc_registration.feature.auth.repository.UserRepository;
import bsc_registration.feature.trainingPlace.TrainingPlace;
import bsc_registration.feature.trainingPlace.repository.TrainingPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {

    private final UserRepository userRepository;
    private final TrainingPlaceRepository placeRepository;

    public List<BscUser> getAllTrainers() {

        return userRepository.findUserByAuthority(AuthorityType.COURSE_OWNER);

    }


    public List<TrainingPlace> getAllTrainingPlaces() {

        return placeRepository.findAll();
    }
}
