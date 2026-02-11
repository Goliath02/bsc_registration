package bsc_registration.feature.registration.sevice;

import bsc_registration.config.AppConfig;
import bsc_registration.feature.registration.dto.BscConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BscConfigService {

    private final AppConfig appConfig;

    public BscConfig getConfig() {
        return new BscConfig(appConfig.getRegistrationReceiver(), appConfig.getPriceList());
    }
}
