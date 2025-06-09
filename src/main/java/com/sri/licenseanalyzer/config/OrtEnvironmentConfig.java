package com.sri.licenseanalyzer.config;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrtEnvironmentConfig {

    @PostConstruct
    public void setupOrt() {
        System.setProperty("org.ossreviewtoolkit.log.level", "info");
    }
}
