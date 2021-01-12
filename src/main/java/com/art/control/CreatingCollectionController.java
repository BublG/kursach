package com.art.control;

import com.art.entity.ItemCollection;
import com.art.entity.User;
import com.art.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        System.out.println(form);
        ItemCollection itemCollection = new ItemCollection(form.get("name"), form.get("description"),
                form.get("topic"), new HashSet<>());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemCollection.setUser(user);
        user.getCollections().add(itemCollection);
        if (!collectionService.saveCollection(itemCollection)) {
            model.addAttribute("nameError", "Collection with the same name already exists");
            return "createCollection";
        }
        return "profile";
    }
}
