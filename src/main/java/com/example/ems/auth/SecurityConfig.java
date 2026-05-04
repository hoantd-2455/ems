package com.example.ems.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable()) // Disable CSRF, because we are using JWT for stateless authentication
                // Stateless session management, server not storing any session, client must
                // send JWT with each request
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Allow unauthenticated access to auth endpoints (e.g. login, register)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Public: Thymeleaf pages, CSS, Actuator, Hello demo
                        .requestMatchers("/employees/**", "/css/**", "/actuator/**",
                                "/hello", "/demo/**")
                        .permitAll()

                        // USER + ADMIN: read data, view stats
                        .requestMatchers(HttpMethod.GET,
                                "/api/employees/**", "/api/departments/**", "/api/stats/**")
                        .hasAnyRole("USER", "ADMIN")

                        // Chỉ ADMIN: create/update/delete data
                        .requestMatchers(HttpMethod.POST,
                                "/api/employees/**", "/api/departments/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")

                        // Any other request must be authenticated
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        // Handle unauthorized (no token or invalid token) -> return 401 with JSON error
                        // message, not redirect to login page
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter()
                                    .write("{\"status\":401,\"message\":\"Unauthorized - token is missing or invalid\"}");
                        })

                        // Handle access denied (authenticated but insufficient permissions)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write(
                                    "{\"status\":403,\"message\":\"Forbidden - insufficient permissions\"}");
                        }))

                // Add JWT authentication filter before the default username/password
                // authentication filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
