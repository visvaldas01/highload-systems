package ru.ifmo.highloadsystems.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import ru.ifmo.highloadsystems.utils.JwtTokensUtils;
import org.springframework.web.server.WebFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter implements WebFilter {

    private final JwtTokensUtils jwtTokensUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var authHeader =  exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        String username = null;
        String jwt = null;

        if (authHeader != null && !authHeader.isEmpty() && authHeader.get(0).startsWith("Bearer ")) {
            jwt = authHeader.get(0).substring(7);
            try {
                username = jwtTokensUtils.getUsername(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("The token lifetime has expired");
            } catch (SignatureException e) {
                log.debug("The signature is incorrect");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtTokensUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        return chain.filter(exchange);
    }
}
