package pl.bajerska.befindyourmeal.edamam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebApplicationController {

    @Autowired
    private ApiService apiConnection;

    @GetMapping("/test")
    public String getInfo(Model model) {
        model.addAttribute("recipe", apiConnection.test());
        return "recipe";
    }
}