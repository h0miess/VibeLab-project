package ru.vibelab.taskmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    @Value("${server-address}")
    private String SERVER_ADDRESS;

    @Value("${server-port}")
    private String SERVER_PORT;

    @Value("${date.pattern}")
    private String DATE_PATTERN;

    @Value("${dateTime.pattern}")
    private String DATE_TIME_PATTERN;
    public String getServerAddress() {
        return SERVER_ADDRESS;
    }

    public String getServerPort() {
        return SERVER_PORT;
    }

    public String getDatePattern() {
        return DATE_PATTERN;
    }

    public String getDateTimePattern() {
        return DATE_TIME_PATTERN;
    }
}
