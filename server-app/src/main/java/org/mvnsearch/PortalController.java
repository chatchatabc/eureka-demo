package org.mvnsearch;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class PortalController {

    @GetMapping("/")
    public String index() {
        return "Hello guest!";
    }

}
