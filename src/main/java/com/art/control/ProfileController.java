package com.art.control;

import com.art.entity.ItemCollection;
import com.art.entity.User;
import com.art.service.CollectionService;
import com.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ProfileController {

    private UserService userService;

    private CollectionService collectionService;

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model, @RequestParam String name) {
        User user = (User) userService.loadUserByUsername(name);
        model.addAttribute("username", name);
        model.addAttribute("collections", user.getItemCollections());
        return "profile";
    }

    @PostMapping("/profile")
    public String profile(Model model, @RequestParam String name, @RequestParam Map<String, String> form) {
        ItemCollection collection = collectionService.findById(Long.parseLong(form.get("id")));
        collectionService.deleteCollection(collection);
        return "redirect:/" + profile(model, name) + "?name=" + name;
    }

}
