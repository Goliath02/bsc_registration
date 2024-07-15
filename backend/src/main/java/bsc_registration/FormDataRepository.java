package bsc_registration;

import bsc_registration.dto.FormData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormDataRepository extends CrudRepository<FormData, Long> {


}
