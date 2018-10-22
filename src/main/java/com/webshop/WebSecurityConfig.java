package com.webshop;

//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.UUID;

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final UUID adminRandomSessionToken;
//
//    public WebSecurityConfig() {
//        adminRandomSessionToken = UUID.randomUUID();
//        System.err.print(adminRandomSessionToken.toString());
//        //TODO create proper logging. minor priority.
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .cors().disable()
//                .authorizeRequests()
//                .antMatchers("/api/session", "/api/categories", "/api/categories/**", "/api/products", "/api/products/**",
//                        "/api/pictures", "/api/pictures/**").anonymous()
//                .anyRequest().authenticated()
//                .and().httpBasic();
//        //TODO configure basic auth
//    }
//
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        //TODO change withDefaultPasswordEncoder minor priority.
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password(adminRandomSessionToken.toString())
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//}