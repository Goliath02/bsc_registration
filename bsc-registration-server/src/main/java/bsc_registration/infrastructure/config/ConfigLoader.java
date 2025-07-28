package bsc_registration.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Configuration
public class ConfigLoader {

    @Value("${config.path}")
    private String configPath;

    public BscCourseConfig loadConfig() {

        final var objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream(configPath)) {
            return objectMapper.readValue(inputStream, BscCourseConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public List<String> loadCourses() {
        final var config = this.loadConfig();
        final var courses = config.getCourses();
        return new ArrayList<>(courses.keySet());
    }
}
