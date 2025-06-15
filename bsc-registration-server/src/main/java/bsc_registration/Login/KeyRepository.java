package bsc_registration.Login;

import bsc_registration.dto.SignUpKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<SignUpKey, String> {

}
