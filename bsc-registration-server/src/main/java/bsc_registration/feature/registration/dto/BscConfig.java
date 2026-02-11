package bsc_registration.feature.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BscConfig {

    private final List<String> courseOptions;
    private final List<String> priceList;

}
