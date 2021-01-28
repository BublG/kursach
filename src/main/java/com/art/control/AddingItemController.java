package com.art.control;

import com.art.entity.Item;
import com.art.entity.ItemCollection;
import com.art.entity.Tag;
import com.art.service.CollectionService;
import com.art.service.ItemService;
import com.art.service.TagService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

@Controller
public class AddingItemController {

    private TagService tagService;

    private CollectionService collectionService;

    private CollectionController collectionController;

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setCollectionController(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @GetMapping("/collection/addItem")
    public String addItem(Model model, @RequestParam Long id) {
        model.addAttribute("tags", tagService.allTagsNames());
        model.addAttribute("id", id);
        return "addItem";
    }

    @PostMapping("/collection/addItem")
    public String addItem(Model model, @RequestParam Map<String, String> form,
                          @RequestParam Long id, Principal principal) {
        ItemCollection collection = collectionService.findById(id);
        Item item = new Item(form.get("name"), collection, new HashSet<>());
        collection.getItems().add(item);
        JSONArray tags = new JSONArray(form.get("tags"));
        for (Object tag : tags) {
            String name = ((JSONObject) tag).getString("value");
            Tag t = tagService.loadTagByName(name);
            if (t != null) {
                item.getTags().add(t);
                t.getItems().add(item);
            } else {
                t = new Tag(name, new HashSet<>());
                t.getItems().add(item);
                item.getTags().add(t);
                tagService.save(t);
            }
        }
        itemService.save(item);
        return collectionController.collections(model, id, principal);
    }
}
