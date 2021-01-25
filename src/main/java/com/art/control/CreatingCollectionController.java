package com.art.control;

import com.art.entity.ItemCollection;
import com.art.entity.User;
import com.art.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Map;

@Controller
public class CreatingCollectionController {

    private CollectionService collectionService;

    @Autowired
    void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/profile/createCollection")
    public String createCollection() {
        return "createCollection";
    }

    @PostMapping("/profile/createCollection")
    public String createCollection(@RequestParam Map<String, String> form, Model model) {
        if (!checkFields(form, model))
            return "createCollection";
        ItemCollection itemCollection = new ItemCollection(form.get("name"),
                form.get("description"), form.get("topic"), new HashSet<>(),
                form.get("image"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemCollection.setUser(user);
        user.getItemCollections().add(itemCollection);
        if (!collectionService.saveCollection(itemCollection)) {
            model.addAttribute("nameError", "Collection with the same name already exists");
            return "createCollection";
        }
        return "redirect:/profile";
    }

    private boolean checkFields(Map<String, String> form, Model model) {
        boolean b = true;
        if (form.get("name").length() < 1) {
            model.addAttribute("nameError", "This field is necessarily");
            b = false;
        }
        if (form.get("description").length() < 1) {
            model.addAttribute("descriptionError", "This field is necessarily");
            b = false;
        }
        return b;
    }
}
