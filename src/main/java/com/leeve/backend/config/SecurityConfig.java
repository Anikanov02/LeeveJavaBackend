package com.leeve.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration authConfig = new CorsConfiguration();
    authConfig.setAllowedOrigins(List.of("*"));
    authConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    authConfig.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

//        source.registerCorsConfiguration("/auth/**", authConfig);
    source.registerCorsConfiguration("/**", authConfig);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/api/v1/scan", "/api/v1/scan/**",
                "/v3/api-docs/**",
                "/v3/api-docs.yaml",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**").permitAll()
//            .requestMatchers(HttpMethod.GET,"/api/v1/report/**").hasAuthority("SCOPE_read:report")
        )
        .cors(withDefaults())
        .csrf(CsrfConfigurer::disable)
//        .oauth2ResourceServer(oauth2 -> oauth2
//            .jwt(withDefaults())
//        )
        .build();
  }
}
