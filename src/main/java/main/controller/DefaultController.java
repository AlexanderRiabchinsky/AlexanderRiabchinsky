package main.controller;

import main.api.response.InitResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    private final InitResponse initResponse;

    public DefaultController(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    @RequestMapping("/")
    public String index(Model model) {
        System.out.println(initResponse.getTitle());
        return "index";//(new Date()).toString();
    }
}
