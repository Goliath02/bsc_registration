package bsc_registration.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigLoader {

    public BscCourseConfig loadConfig() {

        final var objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("/emailConf.json")) {
            return objectMapper.readValue(inputStream, BscCourseConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public List<String> loadCourses() {
        final var config = this.loadConfig();
        final var courses = config.courses();
        return new ArrayList<>(courses.keySet());
    }
}
