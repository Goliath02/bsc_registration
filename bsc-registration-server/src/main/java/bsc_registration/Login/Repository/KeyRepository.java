package bsc_registration.Login.Repository;

import bsc_registration.dto.SignUpKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyRepository extends JpaRepository<SignUpKey, String> {

    @Query("""
            SELECT k FROM SignUpKey k
                WHERE k.key = :signUpKey
            """)
    Optional<SignUpKey> getKeyByKey(final String signUpKey);
}
