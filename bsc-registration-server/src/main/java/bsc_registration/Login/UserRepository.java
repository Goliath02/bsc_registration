package bsc_registration.Login;

import bsc_registration.dto.BscUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BscUser, Long> {

    @Query("SELECT u FROM BscUser u WHERE u.email = :email")
    Optional<BscUser> findByEmail(String email);
}
