package com.example.innova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

  @Autowired
  public UserModelDetailService userService;



  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(registry->{
        registry.requestMatchers("/home","/register/**").permitAll();
        registry.requestMatchers("/admin/**").hasRole("ADMIN");
        registry.requestMatchers("/user/**").hasRole("USER");
        registry.requestMatchers("/user/**").hasRole("ADMIN");
        registry.anyRequest().authenticated();
    })
            .formLogin(httpSecurityFormLoginConfigurer -> {
                httpSecurityFormLoginConfigurer
                .successHandler(new AuthSuccessHandler())
                .permitAll();
            })
    .build();
  }

/*
  @Bean
  public UserModelDetailService userModelDetailService(){
      return userService;
  }*/

  @Bean
  public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }



}
