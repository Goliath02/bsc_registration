package bsc_registration.infrastructure.repository;

import bsc_registration.domain.entities.NswRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NswRegistrationRepository extends JpaRepository<NswRegistration, Integer> {


    @Query("""
    SELECT nswr FROM NSWRegistration AS nswr
    
    """)
    public List<NswRegistration> getAllNswRegistrations();


}
