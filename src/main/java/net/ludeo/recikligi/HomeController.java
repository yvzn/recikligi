package net.ludeo.recikligi;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Setter
@Controller
public class HomeController {

    @Value("${appTitle}")
    private String appTitle;

    @RequestMapping("/")
    public String home(final Model model) {
        model.addAttribute("appTitle", appTitle);

        return "camera";
    }
}
