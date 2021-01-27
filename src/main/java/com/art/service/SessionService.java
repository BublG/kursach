package com.art.service;

import com.art.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SessionService {

    private SessionRegistry sessionRegistry;

    @Autowired
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public void expireUserSessions(Set<String> names) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            System.out.println("SEARCH");
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) principal;
                if (names.contains(userDetails.getUsername())) {
                    for (SessionInformation information : sessionRegistry
                            .getAllSessions(userDetails, true)) {
                        System.out.println("REMOVING");
                        information.expireNow();
                        //sessionRegistry.removeSessionInformation(information.getSessionId());
                    }
                }
            }
        }
    }
}
