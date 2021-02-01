package com.art.control;

import com.art.entity.Item;
import com.art.entity.ItemCollection;
import com.art.service.CollectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomePageController {

    private CollectionService collectionService;

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/")
    public String homePage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        List<ItemCollection> collections = collectionService.findAll();
        collections.sort((o1, o2) -> o2.getItemsCount() - o1.getItemsCount());
        model.addAttribute("collections", collections);
        return "index";
    }
}
