package bsc_registration.features.auth.repository;

import bsc_registration.features.auth.entity.BscAuthority;
import bsc_registration.webInterface.dto.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<BscAuthority, Long> {

	@Query(value = "SELECT auth FROM BscAuthority auth WHERE auth.authority = :authority")
	Optional<BscAuthority> findByAuthority(final AuthorityType authority);
}
