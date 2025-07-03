package bsc_registration.Login;

import bsc_registration.dto.BscUser;
import bsc_registration.dto.SignUpKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BscUser, Long> {

    @Query("SELECT u FROM BscUser u WHERE u.email = :email")
    Optional<BscUser> findByEmail(String email);

    @Query("SELECT u FROM BscUser u WHERE u.userName = :userName")
    Optional<BscUser> findByUsername(String userName);

    @Query("SELECT count(u) > 0 from BscUser u WHERE u.signUpKey = :signUpKey")
    boolean hasUserWithKey(SignUpKey signUpKey);
}
