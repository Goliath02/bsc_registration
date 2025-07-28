package bsc_registration.domain.service;

import bsc_registration.webInterface.dto.AuthorityType;
import bsc_registration.infrastructure.repository.TrainingPlaceRepository;
import bsc_registration.infrastructure.repository.UserRepository;
import bsc_registration.domain.entities.BscUser;
import bsc_registration.domain.entities.TrainingPlace;
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
