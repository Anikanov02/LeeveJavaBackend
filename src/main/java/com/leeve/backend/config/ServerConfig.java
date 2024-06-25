package com.leeve.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
