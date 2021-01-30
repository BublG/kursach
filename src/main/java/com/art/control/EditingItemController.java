package com.art.control;

import com.art.entity.Item;
import com.art.service.ItemService;
import com.art.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditingItemController {

    private ItemService itemService;

    private TagService tagService;

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String editItem(Model model, @RequestParam Long id) {
        Item item = itemService.findItemById(id);
        model.addAttribute("name", item.getName());
        model.addAttribute("id", item.getCollection().getId());
        String myTags = itemService.getTagsNames(item).toString();
        model.addAttribute("myTags", myTags.substring(1, myTags.length() - 1));
        model.addAttribute("tags", tagService.allTagsNames());
        return "addItem";
    }
}
