package bsc_registration;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class NswScheduler {

    private final RegistrationService registrationService;


    @Scheduled(fixedRate = 7, timeUnit = TimeUnit.DAYS)
    public void sendNswRegistrationListToCourseOwner() {




    }

}
