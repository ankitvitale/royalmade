package com.royalmade.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static final String[] PUBLIC_URLS = {

            "/api/appUserRegister",
            "/api/createNewLead",
            "/api/registerAdmin",
            "/api/auth/login" ,
            "/api/employeeregister",
            "/hello",
            "/api/getAllLeads",
            "/{id}/addLogs",
            "/getlead/{id}",
            "/getkLandInfo",
            "/create",
            "/getProjectById/{id}",
            "/getAllland",
            "/land/{id}",
            "/api/getAllProjectsforApp",
            "/api/project/{projectId}",
            "/api/careers",
            "/api/careers/{id}",
            "/api/enquiries",
            "/api/enquiries/{id}",
            "/api/loan-enquiries",
            "/api/loan-enquiries/{id}"

    };


    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS with custom configuration
                .authorizeRequests(auth -> auth
                        .requestMatchers("/test").authenticated() // Require authentication for this endpoint
                        .requestMatchers(PUBLIC_URLS).permitAll() // Allow public access to these URLs
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(point) // Set the authentication entry point
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions
                );

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // Add your custom filter

        return http.build(); // Return the built SecurityFilterChain
    }
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3001"));  // Allow specific origins or use "*"
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Allowed HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "x-auth-token", "Accept"));  // Allowed headers
        configuration.setExposedHeaders(Arrays.asList("Authorization", "x-auth-token"));  // Expose headers to the client
        configuration.setAllowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
        configuration.setMaxAge(3600L);  // Cache preflight response for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Apply CORS configuration to all endpoints
        return source;
    }

}