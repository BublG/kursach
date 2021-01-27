package com.art.control;

import com.art.entity.User;
import com.art.service.SessionService;
import com.art.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
public class UsersTableController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private SessionService sessionService;

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "users";
    }

    @PostMapping("/users")
    public String processUsers(@RequestParam Map<String, String> form, Model model, HttpSession session) {
        Iterator<Map.Entry<String, String>> entries = form.entrySet().iterator();
        entries.next();
        switch (entries.next().getKey()) {
            case "b":
                block(entries);
                break;
            case "d":
                delete(entries);
                break;
            case "u":
                unblock(entries);
        }
        model.addAttribute("allUsers", userService.allUsers());
        return "users";
    }

    private void block(Iterator<Map.Entry<String, String>> entries) {
        Set<String> names = new HashSet<>();
        while (entries.hasNext()) {
            User user = userService.findUserById(Long.parseLong(entries.next().getKey()));
            user.setStatus(0);
            userService.update(user);
            names.add(user.getUsername());
        }
        sessionService.expireUserSessions(names);
    }

    private void unblock(Iterator<Map.Entry<String, String>> entries) {
        while (entries.hasNext()) {
            User user = userService.findUserById(Long.parseLong(entries.next().getKey()));
            user.setStatus(1);
            userService.update(user);
        }
    }

    private void delete(Iterator<Map.Entry<String, String>> entries) {
        Set<String> names = new HashSet<>();
        while (entries.hasNext()) {
            User user = userService.findUserById(Long.parseLong(entries.next().getKey()));
            userService.deleteUser(user.getId());
            names.add(user.getUsername());
        }
        sessionService.expireUserSessions(names);
    }
}
