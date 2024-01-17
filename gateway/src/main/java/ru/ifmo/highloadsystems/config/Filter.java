package ru.ifmo.highloadsystems.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import ru.ifmo.highloadsystems.model.entity.User;
import ru.ifmo.highloadsystems.rest.AuthApi;

import java.util.Optional;

@Component
public class Filter extends AbstractGatewayFilterFactory<Filter.Config> {
    @Autowired
    AuthApi authApi;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader.isEmpty()) {
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authorization header provided"));
            }
            if (!authHeader.startsWith("Bearer ")) {
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header is invalid"));
            }
            String jwtToken = authHeader.substring(7);

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                Optional<User> user = authApi.getUserFromContext();
                if (user.isPresent())
                    response.getHeaders().add("username", "user1");
            }));
        });

    }

    public static class Config {
    }

}
