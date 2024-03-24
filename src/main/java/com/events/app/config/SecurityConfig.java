package com.events.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private UserDetailsService userDetailsService;

	public SecurityConfig(UserDetailsService userDetailsService) {
		super();
		System.out.println(passwordEncoder().encode("nikos"));
		this.setUserDetailsService(userDetailsService);
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf((csrf) -> {

			try {
				csrf.disable().authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/api/**")
						.permitAll().anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		return http.build();
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails testUser1 = User.builder().username("nikos").password(passwordEncoder().encode("nikos"))
//				.roles("USER").build();
//		UserDetails testUser2 = User.builder().username("admin").password(passwordEncoder().encode("nikos"))
//				.roles("ADMIN").build();
//
//		return new InMemoryUserDetailsManager(testUser1, testUser2);
//	}
}
