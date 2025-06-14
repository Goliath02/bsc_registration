package bsc_registration;

import bsc_registration.dto.NswRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NswRegistrationRepository extends JpaRepository<NswRegistration, Integer> {


    @Query("""
    SELECT nswr FROM NSWRegistration AS nswr
    
    """)
    public List<NswRegistration> getAllNswRegistrations();


}
