package com.art.control;

import com.art.entity.User;
import com.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        Integer s = (Integer) oauth2User.getAttributes().get("id");
        if (userService.findUserByName(Integer.toString(s)) == null) {
            User newUser = new User(Integer.toString(s), null,
                    (String) oauth2User.getAttributes().get("email"), 1, new HashSet<>());
            userService.saveOAuthUser(newUser);
        }
        System.out.println(oauth2User);
        return "redirect:/";
    }
}
