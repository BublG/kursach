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
import java.util.HashSet;
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
        if (!checkName(form, model))
            return addItem(model, id);
        if (form.containsKey("edit")) {
            editItem(form);
        } else {
           addItem(form, id);
        }
        return "redirect:/" + collectionController.collections(model, id, principal) + "?id=" + id;
    }

    private boolean checkName(Map<String, String> form, Model model) {
        boolean b = true;
        if (form.get("name").length() == 0) {
            model.addAttribute("nameError", "This field is necessarily");
            b = false;
        }
        if (form.get("tags").length() == 0) {
            model.addAttribute("tagsError", "This field is necessarily");
            b = false;
        }
        return b;
    }

    private void setTags(Map<String, String> form, Item item) {
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
    }

    private void editItem(Map<String, String> form) {
        Item item = itemService.findItemByName(form.get("edit"));
        item.setName(form.get("name"));
        item.getTags().clear();
        setTags(form, item);
        itemService.save(item);
    }

    private void addItem(Map<String, String> form, Long id) {
        ItemCollection collection = collectionService.findById(id);
        Item item = new Item(form.get("name"), collection, new HashSet<>());
        collection.getItems().add(item);
        setTags(form, item);
        itemService.save(item);
    }
}
