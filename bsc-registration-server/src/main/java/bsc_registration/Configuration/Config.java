package bsc_registration.Configuration;

import lombok.NonNull;

import java.util.List;
import java.util.Map;

public record Config(
        @NonNull
        List<String> registrationReceiver,
        @NonNull
        Map<String, List<String>> courses
) {
}
