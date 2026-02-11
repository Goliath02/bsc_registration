package bsc_registration.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class ConfigLoader {

    @Bean
    public AppConfig appConfig(
            @Value("${CONFIG_PATH:classpath:bscConf.json}") String configPath
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Resource resource;
        if (configPath.startsWith("classpath:")) {
            // Load from classpath (inside JAR/resources)
            String classpathLocation = configPath.substring("classpath:".length());
            resource = new ClassPathResource(classpathLocation);
        } else {
            // Load from filesystem (external config file)
            resource = new FileSystemResource(configPath);
        }

        if (! resource.exists()) {
            throw new IllegalStateException("Config file not found at: " + configPath);
        }

        AppConfig config;
        try (InputStream inputStream = resource.getInputStream()) {
            config = objectMapper.readValue(inputStream, AppConfig.class);
        }

        if (config.getRegistrationReceiver() == null || config.getRegistrationReceiver().isEmpty()) {
            throw new IllegalStateException("registrationReceiver must not be empty");
        }

        return config;
    }
}