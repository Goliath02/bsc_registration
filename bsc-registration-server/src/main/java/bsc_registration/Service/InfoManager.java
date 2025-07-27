package bsc_registration.Service;

import bsc_registration.Login.Repository.TrainingPlaceRepository;
import bsc_registration.Login.Repository.UserRepository;
import bsc_registration.dto.AuthorityType;
import bsc_registration.dto.BscUser;
import bsc_registration.dto.TrainingPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoManager {

	private final UserRepository userRepository;
	private final TrainingPlaceRepository placeRepository;

	public List<BscUser> getAllTrainers() {

		return userRepository.findUserByAuthority(AuthorityType.COURSE_OWNER);

	}


	public List<TrainingPlace> getAllTrainingPlaces() {

		return placeRepository.findAll();
	}
}
