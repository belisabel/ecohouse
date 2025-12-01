package com.EcoHouse.config;

import com.EcoHouse.auth.service.CustomerDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomerDetailsService customerDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml"
                        ).permitAll()
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/api/customers/**").permitAll()
                        .requestMatchers("/api/customers/current").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/brand/**").hasRole("BRAND_ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .anyRequest().permitAll() // Permitir acceso a todo temporalmente
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * este bean está siendo creado para:
     * ✅ Habilita CORS para todos los orígenes (allowedOrigins("*"))
     * ✅ Permite todos los métodos HTTP (GET, POST, PUT, DELETE, PATCH, OPTIONS)
     * ✅ Permite todos los headers
     * ✅ Aplica a todos los endpoints (/**)
     * ✅ Sin errores de compilación
     **/
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")  // Usar allowedOriginPatterns en lugar de allowedOrigins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)  // Necesario para que funcione con Spring Security
                        .maxAge(3600);
            }
        };
    }


}
