package net.ludeo.recikligi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LimitController {

    @GetMapping("/limit")
    public String limit() {
        return "limit";
    }
}
