package bsc_registration.features.auth.repository;

import bsc_registration.features.info.entity.BscMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BscMemberRepository extends JpaRepository<BscMember, Long> {


}
