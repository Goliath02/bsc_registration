package bsc_registration.infrastructure.repository;

import bsc_registration.domain.entities.BscMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BscMemberRepository extends JpaRepository<BscMember, Long> {


}
