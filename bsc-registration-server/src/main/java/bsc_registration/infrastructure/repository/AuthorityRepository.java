package bsc_registration.infrastructure.repository;

import bsc_registration.webInterface.dto.AuthorityType;
import bsc_registration.domain.entities.BscAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<BscAuthority, Long> {

	@Query(value = "SELECT auth FROM BscAuthority auth WHERE auth.authority = :authority")
	Optional<BscAuthority> findByAuthority(final AuthorityType authority);
}
