package bsc_registration.domain.service;

import bsc_registration.domain.entities.BscAuthority;
import bsc_registration.domain.entities.BscUser;
import bsc_registration.domain.entities.SignUpKey;
import bsc_registration.features.auth.repository.AuthorityRepository;
import bsc_registration.features.auth.repository.UserRepository;
import bsc_registration.webInterface.dto.AuthorityType;
import bsc_registration.webInterface.dto.TrainerDto;
import bsc_registration.webInterface.dto.TrainerInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorityRepository authRepository;
    private final AuthService authService;

    public List<TrainerInfoDto> getAllTrainers() {

        List<BscUser> userByAuthority = userRepository.findUserByAuthorityId(AuthorityType.COURSE_OWNER);

        return userByAuthority.stream().map(trainer -> new TrainerInfoDto(trainer.getUserId(), trainer.getFullName())).toList();
    }

    public void createTrainer(final TrainerDto trainerDto) throws IllegalArgumentException {

        Optional<BscUser> byEmail = userRepository.findByEmail(trainerDto.getEmail());

        if (byEmail.isPresent()) {
            throw new IllegalArgumentException("User with email " + trainerDto.getEmail() + " already exists");
        }

        BscUser newTrainer = new BscUser();

        newTrainer.setEmail(trainerDto.getEmail());
        newTrainer.setPassword(bCryptPasswordEncoder.encode(trainerDto.getPassword()));
        newTrainer.setUserName(trainerDto.getUsername());

        final BscAuthority adminAuthority = authRepository.findByAuthority(AuthorityType.COURSE_OWNER)
                .orElseGet(() -> authRepository.save(new BscAuthority(null, AuthorityType.COURSE_OWNER)));

        newTrainer.setAuthorities(List.of(adminAuthority));

        final SignUpKey signUpKey = authService.createSignUpKey(AuthorityType.ADMIN);
        newTrainer.setSignUpKey(signUpKey);

        userRepository.save(newTrainer);
    }

    public void updateTrainerName(final long trainerId, final String trainerName) throws IllegalArgumentException {
        final BscUser trainer = userRepository.findById(trainerId).orElseThrow(() -> new IllegalArgumentException("User with id " + trainerId + " does not " +
                "exists"));

        trainer.setUserName(trainerName);
        userRepository.save(trainer);
    }

    public void deleteTrainer(final long trainerId) {
        userRepository.deleteById(trainerId);
    }
}
