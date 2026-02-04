package bsc_registration.feature.registration.repository;

import bsc_registration.feature.registration.entities.BscMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BscMemberRepository extends JpaRepository<BscMember, Long> {


}
