package net.ludeo.recikligi.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

@Setter
abstract class AbstractController {

    @Value("${recikligi.appTitle}")
    protected String appTitle;

    protected Model addCommonAttributes(Model model) {
        return model.addAttribute("appTitle", appTitle);
    }
}
