package ru.ifmo.highloadsystems.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import ru.ifmo.highloadsystems.rest.UserApi;
import ru.ifmo.highloadsystems.service.UserService;
import reactor.core.publisher.Mono;
import org.springframework.security.config.web.server.ServerHttpSecurity.CorsSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;

@Component
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private UserService userService;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void setUserService(UserApi userApi) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @SneakyThrows
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/albums/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                        .pathMatchers("/musicians").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                        .pathMatchers("/scrobbles").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                        .pathMatchers("/scrobbles/stats").permitAll()
                        .pathMatchers("/songs").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                        .pathMatchers("/songs/add_to").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                        .pathMatchers("/songs/recommendations").permitAll()
                        .pathMatchers("/tags/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                        .pathMatchers("/tagGroups/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
                        .anyExchange().permitAll())
                .exceptionHandling(
                        exception -> exception
                                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))))
                .csrf(CsrfSpec::disable)
                .cors(CorsSpec::disable);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
