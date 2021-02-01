package com.art.control;

import com.art.entity.ItemCollection;
import com.art.service.CollectionService;
import com.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class EditingCollectionController {

    private CollectionService collectionService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/profile/editCollection")
    public String editCollection(Model model, @RequestParam Long id, Principal principal) {
        ItemCollection collection = collectionService.findCollectionById(id);
        if (!collection.getUser().getUsername().equals(principal.getName())
                && !userService.findUserByName(principal.getName()).isAdmin()) {
            return "index";
        }
        model.addAttribute("name", collection.getName());
        model.addAttribute("descr", collection.getDescription());
        model.addAttribute("topic", collection.getTopic());
        model.addAttribute("id", id);
        return "createCollection";
    }
}
