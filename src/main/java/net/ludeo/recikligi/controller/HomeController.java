package net.ludeo.recikligi.controller;

import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Setter
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "redirect:/camera";
    }
}
