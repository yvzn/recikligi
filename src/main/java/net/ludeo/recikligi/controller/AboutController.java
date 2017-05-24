package net.ludeo.recikligi.controller;

import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Setter
@Controller
public class AboutController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
