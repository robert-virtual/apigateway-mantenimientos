package com.example.apigatewaymantenimientos;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouteValidator validator;
    private final JwtService jwtService;

    public AuthenticationFilter(RouteValidator validator, JwtService jwtService) {
        super(Config.class);
        this.validator = validator;
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }
                String authorization = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authorization == null || !authorization.startsWith("Bearer "))
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                String token = authorization.substring("Bearer ".length());
                if(!jwtService.isTokenValid(token)){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                };
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config {

    }
}
