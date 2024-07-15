package bsc_registration;


import bsc_registration.dto.FormData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final RegistrationModule registrationModule;

    public RegistrationController(RegistrationModule registrationModule) {
        this.registrationModule = registrationModule;
    }

    @GetMapping("/")
    public String getFrontend(){
        return "frontend";
    }

    @PostMapping("/registrate")
    public void validateRegistration(FormData formData){
        registrationModule.saveFormData(formData);
    }

}
