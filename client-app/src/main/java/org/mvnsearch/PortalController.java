package org.mvnsearch;

import org.mvnsearch.infra.UserHttpService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class PortalController {
    private final ReactiveDiscoveryClient discoveryClient;
    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final UserHttpService userHttpService;

    public PortalController(ReactiveDiscoveryClient discoveryClient,
                            WebClient.Builder webClientBuilder,
                            UserHttpService userHttpService) {
        this.discoveryClient = discoveryClient;
        this.loadBalancedWebClientBuilder = webClientBuilder;
        this.userHttpService = userHttpService;
    }

    @GetMapping("/")
    public String index() {
        return "Hello";
    }

    @GetMapping("/instances/{name}")
    public Mono<List<ServiceInstance>> instances(@PathVariable("name") String name) {
        return discoveryClient.getInstances(name).collectList();
    }

    @GetMapping("/services")
    public Flux<String> services() {
        return discoveryClient.getServices();
    }

    @GetMapping("/server-call")
    public Mono<String> callServer() {
        return userHttpService.index().map(s -> "Response from server-app: " + s);
    }

    @GetMapping("/server-call2")
    public Mono<String> callServer2() {
        return loadBalancedWebClientBuilder.build()
                .get()
                .uri("http://server-app/")
                .retrieve()
                .bodyToMono(String.class)
                .map(s -> "Response from server-app: " + s);
    }
}
