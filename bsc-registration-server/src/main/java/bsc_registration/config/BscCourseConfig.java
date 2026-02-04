package bsc_registration.config;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BscCourseConfig {

    private List<String> registrationReceiver;
    private Map<String, List<String>> courses;
    private List<String> priceList;

}