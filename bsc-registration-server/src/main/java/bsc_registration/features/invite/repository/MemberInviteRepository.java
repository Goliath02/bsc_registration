package bsc_registration.features.invite.repository;

import bsc_registration.features.invite.entity.MemberInvite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInviteRepository extends JpaRepository<MemberInvite, Long> {
}
