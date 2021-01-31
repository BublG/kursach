package com.art.control;

import com.art.entity.User;
import com.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;

@Controller
public class OAuth2Controller {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/gitLogin")
    public String securedPage(@AuthenticationPrincipal OAuth2User oauth2User) {
        saveUser(oauth2User);
        return "redirect:/";
    }

    private void saveUser(OAuth2User oauth2User) {
        String name = oauth2User.getName();
        if (userService.findUserByName(name) == null) {
            User newUser = new User(name, null,
                    (String) oauth2User.getAttributes().get("email"), 1, new HashSet<>());
            userService.saveOAuthUser(newUser);
        }
    }
}
