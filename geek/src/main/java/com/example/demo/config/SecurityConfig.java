package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http
    			.authorizeHttpRequests((requests) -> requests
            	.requestMatchers("/admin/signup", "/admin/signin").permitAll()
            	.requestMatchers("/admin/dashboard").authenticated()
            	.anyRequest().authenticated()
    		)
    			.formLogin((formLogin) -> formLogin
    			.loginPage("/admin/signin")
    			.defaultSuccessUrl("/admin/dashboard", true)
    			.permitAll()
   			)
    			.logout((logout) -> logout
    			.logoutUrl("/logout")
    			.logoutSuccessUrl("/admin/signin")
    			.permitAll()
    		)
    			.userDetailsService(customUserDetailsService) // CustomUserDetailsServiceの登録
    			.build();
	}
}
