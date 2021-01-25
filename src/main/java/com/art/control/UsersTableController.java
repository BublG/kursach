package com.art.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersTableController {

    @GetMapping("/users")
    public String users(Model model) {
        return "users";
    }
}
