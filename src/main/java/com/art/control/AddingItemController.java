package com.art.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddingItemController {

    @GetMapping("/collection/addItem")
    public String addItem() {
        return "addItem";
    }
}
