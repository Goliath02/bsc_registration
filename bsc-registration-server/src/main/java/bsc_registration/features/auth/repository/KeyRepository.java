package bsc_registration.features.auth.repository;

import bsc_registration.features.auth.entity.SignUpKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyRepository extends JpaRepository<SignUpKey, Long> {

	@Query("""
			SELECT k FROM SignUpKey k
			    WHERE k.key = :signUpKey
			""")
	Optional<SignUpKey> getKeyByKey(final String signUpKey);
}
