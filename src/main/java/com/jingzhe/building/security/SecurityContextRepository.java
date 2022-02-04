package com.jingzhe.building.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private static final String BEARER = "Bearer ";

    private final TokenAuthManager tokenAuthManager;

    public SecurityContextRepository(TokenAuthManager tokenAuthManager) {
        this.tokenAuthManager = tokenAuthManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        return Mono.justOrEmpty(swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
            .filter(authHeader -> authHeader.startsWith(BEARER))
            .flatMap(authHeader -> {
                String authToken = authHeader.substring(BEARER.length());
                Authentication auth = new BearerTokenAuthenticationToken(authToken);
                return this.tokenAuthManager.authenticate(auth).map(SecurityContextImpl::new);
            });
    }
}
