package bsc_registration.feature.registration.repository;

import bsc_registration.feature.registration.entities.NswRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NswRegistrationRepository extends JpaRepository<NswRegistration, Integer> {


	@Query("""
			SELECT nswr FROM NSWRegistration AS nswr
			
			""")
	public List<NswRegistration> getAllNswRegistrations();


}
