package com.art.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreatingCollectionController {

    @GetMapping("/profile/createCollection")
    public String createCollection() {
        return "createCollection";
    }
}
