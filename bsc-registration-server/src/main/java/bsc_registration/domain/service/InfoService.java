package bsc_registration.domain.service;

import bsc_registration.domain.entities.BscUser;
import bsc_registration.domain.entities.CourseType;
import bsc_registration.domain.entities.TrainingPlace;
import bsc_registration.features.course.repository.CourseTypeRepository;
import bsc_registration.features.trainingPlace.repository.TrainingPlaceRepository;
import bsc_registration.features.auth.repository.UserRepository;
import bsc_registration.webInterface.dto.AuthorityType;
import bsc_registration.webInterface.dto.TrainerInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {

  private final UserRepository userRepository;
  private final TrainingPlaceRepository placeRepository;
  private final CourseTypeRepository courseTypeRepository;

  @Deprecated
  public List<TrainerInfoDto> getAllTrainers() {

    List<BscUser> userByAuthority = userRepository.findUserByAuthorityId(AuthorityType.COURSE_OWNER);

    return userByAuthority.stream().map(trainer -> new TrainerInfoDto(trainer.getUserId(), trainer.getFullName())).toList();
  }

  @Deprecated
  public List<TrainingPlace> getAllTrainingPlaces() {
    return placeRepository.findAll();
  }

  public List<CourseType> getAllCourseTypes() {
    return courseTypeRepository.findAll();
  }
}
