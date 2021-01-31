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

import java.security.Principal;
import java.util.HashSet;
import java.util.Map;

@Controller
public class CreatingCollectionController {

    private CollectionService collectionService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/profile/createCollection")
    public String createCollection() {
        return "createCollection";
    }

    @PostMapping("/profile/createCollection")
    public String createCollection(@RequestParam Map<String, String> form, Model model, Principal principal) {
        if (!checkFields(form, model))
            return "createCollection";
        User user = userService.findUserByName(principal.getName());
        if (form.containsKey("edit")) {
            editing(form);
        } else {
            creating(form, user);
        }
        return "redirect:/profile?name=" + user.getUsername();
    }

    private boolean checkFields(Map<String, String> form, Model model) {
        boolean b = true;
        if (form.get("name").length() == 0) {
            model.addAttribute("nameError", "This field is necessarily");
            b = false;
        }
        if (form.get("description").length() == 0) {
            model.addAttribute("descriptionError", "This field is necessarily");
            b = false;
        }
        return b;
    }

    private void editing(Map<String, String> form) {
        ItemCollection collection = collectionService.findCollectionById(Long.parseLong(form.get("edit")));
        collection.setFields(form.get("name"), form.get("description"), form.get("topic"));
        if (form.get("image").length() != 0)
            collection.setImage(form.get("image"));
        collectionService.saveCollection(collection);
    }

    private void creating(Map<String, String> form, User user) {
        ItemCollection itemCollection = new ItemCollection(form.get("name"),
                form.get("description"), form.get("topic"), new HashSet<>(), form.get("image"));
        itemCollection.setUser(user);
        user.getItemCollections().add(itemCollection);
        collectionService.saveCollection(itemCollection);
    }
}
