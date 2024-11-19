package com.ganzithon.go_farming.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// Security Filter Chain 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// 접근 허용 URL 설정
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(new AntPathRequestMatcher("/users/**"),
								new AntPathRequestMatcher("/province/**"),
								new AntPathRequestMatcher("/category")).permitAll() // 사용자 API 공개
						.anyRequest().authenticated() // 그 외의 요청은 인증 필요
				)
				// CSRF 보호 비활성화 (API를 위해)
				.csrf(csrf -> csrf.disable())
				// CORS 설정 추가
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				// 폼 로그인 설정
				.formLogin(formLogin -> formLogin.disable()
				)
				// 로그아웃 설정
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/users/logout","POST")) // 로그아웃 요청 매핑
						.logoutSuccessHandler(customLogoutSuccessHandler()) // 커스텀 핸들러 추가
						.invalidateHttpSession(true) // 세션 무효화
						.deleteCookies("JSESSIONID") // 쿠키 삭제
				)
				.sessionManagement(sessionManagement -> sessionManagement
						.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요 시 세션 생성
						.maximumSessions(1) // 사용자당 세션 최대 개수 설정
						.maxSessionsPreventsLogin(false) // 새로운 로그인을 허용 (기존 세션 만료)
				)

				// 세션 무효화 URL 추가
				.sessionManagement(sessionManagement -> sessionManagement
						.invalidSessionUrl("/users/login") // 세션 무효화 시 리디렉션 경로 설정
				);

		return http.build();
	}

	// 커스텀 로그아웃 성공 핸들러
	@Bean
	LogoutSuccessHandler customLogoutSuccessHandler() {
		return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) -> {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate(); // 세션 무효화
			}
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json");

			Map<String, String> responseBody = new HashMap<>();
			responseBody.put("message", "로그아웃 성공");

			ObjectMapper objectMapper = new ObjectMapper();
			response.getWriter().write(objectMapper.writeValueAsString(responseBody));
		};
	}


	// CORS 설정 소스 메서드 정의
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8080", "https://go-farming.shop")); // 허용할 프론트엔드/백엔드 주소
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // PATCH 메서드 추가
		config.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
		config.setAllowCredentials(true); // 자격 증명 허용 (예: 쿠키)

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 설정 적용
		return source;
	}

	// PasswordEncoder 설정
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

