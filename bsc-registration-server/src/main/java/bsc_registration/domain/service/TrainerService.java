package bsc_registration.domain.service;

import bsc_registration.domain.entities.BscUser;
import bsc_registration.infrastructure.repository.MemberInviteRepository;
import bsc_registration.infrastructure.repository.UserRepository;
import bsc_registration.webInterface.dto.AuthorityType;
import bsc_registration.webInterface.dto.TrainerDto;
import bsc_registration.webInterface.dto.TrainerInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final UserRepository userRepository;
    private final MemberInviteRepository inviteRepository;

    public List<TrainerInfoDto> getAllTrainers() {

        List<BscUser> userByAuthority = userRepository.findUserByAuthorityId(AuthorityType.COURSE_OWNER);

        return userByAuthority.stream().map(trainer -> new TrainerInfoDto(trainer.getUserId(), trainer.getFullName())).toList();
    }

    public void createTrainer(final TrainerDto trainerDto) {





    }

    public void updateTrainer(final long trainerId, final TrainerDto trainerDto) {}

    public void deleteTrainer(final long trainerId, final TrainerInfoDto trainerInfoDto) {}
}
