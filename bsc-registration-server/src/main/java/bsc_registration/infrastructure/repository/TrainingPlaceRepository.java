package bsc_registration.infrastructure.repository;

import bsc_registration.domain.entities.TrainingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingPlaceRepository extends JpaRepository<TrainingPlace, Long> {


}
