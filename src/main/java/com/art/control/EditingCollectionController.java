package com.art.control;

import com.art.entity.ItemCollection;
import com.art.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditingCollectionController {

    private CollectionService collectionService;

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/profile/editCollection")
    public String editCollection(Model model, @RequestParam Long id) {
        ItemCollection collection = collectionService.findCollectionById(id);
        model.addAttribute("name", collection.getName());
        model.addAttribute("descr", collection.getDescription());
        model.addAttribute("topic", collection.getTopic());
        model.addAttribute("id", id);
        return "createCollection";
    }
}
