package com.art.control;

import com.art.entity.ItemCollection;
import com.art.entity.User;
import com.art.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CollectionController {

    private CollectionService collectionService;

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/collection")
    public String collections(Model model, @RequestParam Long id, Principal principal) {
        ItemCollection collection = collectionService.findById(id);
        String username = collection.getUser().getUsername(); //owner
        model.addAttribute("image", collection.getImage());
        model.addAttribute("name", collection.getName());
        model.addAttribute("descr", collection.getDescription());
        model.addAttribute("topic", collection.getTopic());
        model.addAttribute("creator", username);
        model.addAttribute("items", collection.getItems());
        if (principal != null) {
            User user = (User) principal;
            if (user.getUsername().equals(username) || user.isAdmin()) {
                model.addAttribute("owner", true);
            }
        }
        return "collection";
    }
}
