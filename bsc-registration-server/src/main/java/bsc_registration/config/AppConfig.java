package bsc_registration.config;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties
public class AppConfig {

    @JsonProperty("registrationReceiver")
    private List<String> registrationReceiver;

    @JsonProperty("courses")
    private Map<String, List<String>> courses;

    @JsonProperty("priceList")
    private List<String> priceList;

}
