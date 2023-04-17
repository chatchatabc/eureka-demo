package org.mvnsearch.infra;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface UserHttpService {
    @GetExchange("/")
    Mono<String> index();

    @GetMapping("/user/{id}")
    Mono<User> findUserById(@PathVariable String id);

    record User(String id, String name) {
    }
}
