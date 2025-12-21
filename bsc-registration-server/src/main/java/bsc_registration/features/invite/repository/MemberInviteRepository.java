package bsc_registration.features.invite.repository;

import bsc_registration.domain.entities.MemberInvite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInviteRepository extends JpaRepository<MemberInvite, Long> {
}
