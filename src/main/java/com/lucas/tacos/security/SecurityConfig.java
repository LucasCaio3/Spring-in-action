package com.lucas.tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.lucas.tacos.User;
import com.lucas.tacos.data.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			User user = userRepo.findByUsername(username);
			if (user != null)
				return user;
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}

	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		http.authorizeHttpRequests((authorize) -> {
			try {
				authorize.requestMatchers(mvc.pattern("/design")).hasRole("USER")
						.requestMatchers(mvc.pattern("/orders")).hasRole("USER")
						.requestMatchers(mvc.pattern("/h2-console/**")).permitAll()
						.anyRequest().permitAll()
						.and().headers().frameOptions().sameOrigin()
						.and().formLogin()
						.loginPage("/login")
						.defaultSuccessUrl("/design", true)
						.and().logout().logoutUrl("/logout");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		//desabilitei o csrf para poder acessar o h2
		http.csrf().disable();
		return http.build();
	}
}