package com.tecsup.app.micro.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @GetMapping("/routes")
    public Flux<RouteDefinition> getRoutes() {
        return routeDefinitionLocator.getRouteDefinitions();
    }

}
