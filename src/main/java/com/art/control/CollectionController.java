package com.art.control;

import com.art.entity.Item;
import com.art.entity.ItemCollection;
import com.art.entity.User;
import com.art.service.CollectionService;
import com.art.service.ItemService;
import com.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
public class CollectionController {

    private CollectionService collectionService;

    private ItemService itemService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/collection")
    public String collections(Model model, @RequestParam Long id, Principal principal) {
        ItemCollection collection = collectionService.findCollectionById(id);
        String username = collection.getUser().getUsername(); //owner
        model.addAttribute("collection", collection);
        model.addAttribute("creator", username);
        if (principal != null) {
            model.addAttribute("username", principal.getName());
            User user = userService.findUserByName(principal.getName());
            if (user.getUsername().equals(username) || user.isAdmin()) {
                model.addAttribute("owner", true);
            }
        }
        return "collection";
    }

    @PostMapping("/collection")
    public String collections(Model model, @RequestParam Long id, @RequestParam Map<String, String> form, Principal p) {
        ItemCollection collection = collectionService.findCollectionById(id);
        Item item = itemService.findItemById(Long.parseLong(form.get("item")));
        collection.getItems().remove(item);
        itemService.deleteItem(item);
        return "redirect:/" + collections(model, id, p) + "?id=" + id;
    }
}
