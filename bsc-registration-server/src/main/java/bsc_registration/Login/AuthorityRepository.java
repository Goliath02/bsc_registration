package bsc_registration.Login;

import bsc_registration.dto.BscAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<BscAuthority, Long> {

	Optional<BscAuthority> findByAuthority(final String authority);
}
