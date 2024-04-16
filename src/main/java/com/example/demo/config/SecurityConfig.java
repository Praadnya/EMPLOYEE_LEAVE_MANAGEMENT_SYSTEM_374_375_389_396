package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

import com.example.demo.service.EmployeeService;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final EmployeeService employeeService;

    public SecurityConfig(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(employeeService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                .requestMatchers("/manager/**").hasRole("MANAGER")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .successHandler((request, response, authentication) -> {
                    String targetUrl = determineTargetUrl(authentication);
                    response.sendRedirect(targetUrl);
                })
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Specify logout URL
                .logoutSuccessUrl("/login?logout") // Specify logout success URL
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/employee/create-request", "/logout") // Ignore CSRF for /logout endpoint
            );

        return http.build();
    }


    private String determineTargetUrl(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals("ROLE_EMPLOYEE")) {
            return "/employee/pending-requests";
        } else if (role.equals("ROLE_MANAGER")) {
            return "/manager/";
        } else {
            throw new IllegalStateException("Unexpected role: " + role);
        }
    }
}
