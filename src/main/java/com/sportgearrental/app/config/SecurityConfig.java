package com.sportgearrental.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "css/**", "/js/**", "/iamges/**", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form ->form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout (logout -> logout
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());
        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService (){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser (
                User.withUsername ("user")
                        .password ("{noop}password")
                        .roles ("USER")
                        .build()
        );
        manager.createUser(
                User.withUsername("admin")
                        .password("{noop}admin123")
                        .roles ("ADMIN")
                        .build()
        );
        return manager;
    }

}
