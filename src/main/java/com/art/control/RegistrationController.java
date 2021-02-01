package com.art.control;

import com.art.entity.User;
import com.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "registration";
        userForm.setStatus(1);
        userForm.setItemCollections(new HashSet<>());
        if (!userService.saveUser(userForm)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "index";
    }
}
