package bsc_registration.Login.Repository;

import bsc_registration.dto.AuthorityType;
import bsc_registration.dto.BscUser;
import bsc_registration.dto.SignUpKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BscUser, Long> {

    @Query("SELECT u FROM BscUser u WHERE u.email = :email")
    Optional<BscUser> findByEmail(String email);

    @Query("SELECT u FROM BscUser u WHERE u.userName = :userName")
    Optional<BscUser> findByUsername(String userName);

    @Query("SELECT count(u) > 0 from BscUser u WHERE u.signUpKey = :signUpKey")
    boolean hasUserWithKey(SignUpKey signUpKey);

    @Query("SELECT u FROM BscUser u JOIN u.authorities a WHERE a.authority = :authority")
    List<BscUser> findUserByAuthority(final AuthorityType authority);
}
