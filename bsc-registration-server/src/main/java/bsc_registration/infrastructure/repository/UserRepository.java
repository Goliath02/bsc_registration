package bsc_registration.infrastructure.repository;

import bsc_registration.domain.entities.BscUser;
import bsc_registration.domain.entities.SignUpKey;
import bsc_registration.webInterface.dto.AuthorityType;
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

	@Query("SELECT u FROM BscUser u WHERE u.signUpKey.authority.authority = :authority")
	List<BscUser> findUserByAuthorityId(final AuthorityType authority);

    @Query("SELECT u FROM BscUser u WHERE u.signUpKey.signUpKeyId = :signUpKeyId")
    Optional<BscUser> findBySignUpKeyId(long signUpKeyId);
}
