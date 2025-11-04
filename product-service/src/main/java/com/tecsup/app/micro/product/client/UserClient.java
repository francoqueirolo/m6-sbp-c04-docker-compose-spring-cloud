package com.tecsup.app.micro.product.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
//@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class UserClient {

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;


    @CircuitBreaker(name = "userService",
            fallbackMethod = "getUserByIdFallback")
    public User getUserById(Long createdBy) {

        String url = userServiceUrl + "/api/users/" + createdBy;
        User usr = restTemplate.getForObject(url, User.class);
        log.info("User retrieved successfully from userdb: {}", usr);
        return usr;

    }


    private User getUserByIdFallback(Long createdBy, Throwable throwable) {
        log.warn("Fallback method invoked for getUserById due to: {}", throwable.getMessage());
        return User.builder()
                .id(createdBy)
                .name("Unknown User")
                .email("Unknown Email")
                .phone("Unknown Phone")
                .address("Unknown Address")
                .build();
    }
}
