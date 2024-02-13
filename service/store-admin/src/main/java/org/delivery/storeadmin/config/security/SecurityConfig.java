package org.delivery.storeadmin.config.security;

import java.util.List;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Security 활성화
public class SecurityConfig {

	private List<String> SWAGGER = List.of(
		"/swagger-ui.html",
		"/swagger-ui/**",
		"/v3/api-docs/**"
	);

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(it -> {
				it.
					// 1. resource 에 대해 모든 요청 허용
						requestMatchers(
						PathRequest.toStaticResources().atCommonLocations()
					).permitAll()
					// 2. swagger 는 인증 없이 통과
					.requestMatchers(
						SWAGGER.toArray(new String[0])
					).permitAll()
					// 3. open-api 하위 모든 주소는 인증 없이 통과
					.requestMatchers(
						"/open-api/**"
					).permitAll()
					// 그 외 모든 요청은 인증 사용
					.anyRequest().authenticated();
			})
			.formLogin(Customizer.withDefaults());

		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// hash 방식 암호화, salt 를 추가 해싱
		// 디코딩 불가능하며, 인코딩만 가능하다
		return new BCryptPasswordEncoder();
	}
}
