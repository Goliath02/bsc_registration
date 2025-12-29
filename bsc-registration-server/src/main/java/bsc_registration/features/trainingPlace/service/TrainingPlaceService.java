package bsc_registration.features.trainingPlace.service;

import bsc_registration.features.trainingPlace.entity.TrainingPlace;
import bsc_registration.features.trainingPlace.repository.TrainingPlaceRepository;
import bsc_registration.webInterface.dto.TrainingPlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlaceService {


    private final TrainingPlaceRepository placeRepository;

    public List<TrainingPlace> getAllTrainingPlaces() {
        return placeRepository.findAll();
    }

    public void saveTrainingPlace(final TrainingPlaceDto dto) {

        final TrainingPlace trainingPlace = new TrainingPlace();

        trainingPlace.setName(dto.getName());
        trainingPlace.setStreet(dto.getStreet());
        trainingPlace.setHouseNumber(dto.getHouseNumber());
        trainingPlace.setStreetNumberAddition(dto.getStreetNumberAddition());
        trainingPlace.setCity(dto.getCity());
        trainingPlace.setPostalCode(dto.getPostalCode());

        placeRepository.save(trainingPlace);
    }

    public void updateTrainingPlace(final Long trainingPlaceId, final TrainingPlaceDto dto) {

        TrainingPlace trainingPlace = placeRepository.findById(trainingPlaceId).orElseThrow();

        trainingPlace.setName(dto.getName());
        trainingPlace.setStreet(dto.getStreet());
        trainingPlace.setCity(dto.getCity());
        trainingPlace.setPostalCode(dto.getPostalCode());
        placeRepository.save(trainingPlace);
    }

    public void deleteTrainingPlace(final Long trainingPlaceId) {
        placeRepository.deleteById(trainingPlaceId);
    }

}
