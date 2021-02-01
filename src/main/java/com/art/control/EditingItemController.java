package com.art.control;

import com.art.entity.Item;
import com.art.service.ItemService;
import com.art.service.TagService;
import com.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class EditingItemController {

    private ItemService itemService;

    private TagService tagService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String editItem(Model model, @RequestParam Long id, Principal principal) {
        Item item = itemService.findItemById(id);
        if (!item.getCollection().getUser().getUsername().equals(principal.getName())
                && !userService.findUserByName(principal.getName()).isAdmin()) {
            return "index";
        }
        model.addAttribute("name", item.getName());
        model.addAttribute("itemId", id);
        model.addAttribute("id", item.getCollection().getId());
        String myTags = itemService.getTagsNames(item).toString();
        model.addAttribute("myTags", myTags.substring(1, myTags.length() - 1));
        model.addAttribute("tags", tagService.allTagsNames());
        return "addItem";
    }
}
