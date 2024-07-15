package bsc_registration;

import bsc_registration.dto.FormData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RegistrationModule {

    private FormDataRepository repository;

    public void saveFormData(FormData data){

        if (isValidData(data)) {
            repository.save(data);
        }


    }


    private boolean isValidData(FormData data) {

        if (data.getIBAN().length() != 22) {
            return false;
        }


        return false;
    }

    private boolean isValidEmail(FormData data) {


        return false;
    }

}
