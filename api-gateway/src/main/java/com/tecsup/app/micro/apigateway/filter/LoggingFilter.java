package com.tecsup.app.micro.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Incoming request data:");
        log.info("Method: " + exchange.getRequest().getMethod());
        log.info("Path: " + exchange.getRequest().getPath());
        log.info("URI: " + exchange.getRequest().getURI());
        return chain.filter(exchange).then(
            reactor.core.publisher.Mono.fromRunnable(() -> {
                log.info("Outgoing response data:");
                log.info("Response Status Code: " + exchange.getResponse().getStatusCode());
            })
        );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
