package edu.netcracker.backend.security;

import edu.netcracker.backend.model.User;
import edu.netcracker.backend.service.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j(topic = "log")
public class SecurityContext {

    private final UserService userService;
    private User user;

    @Autowired
    public SecurityContext(UserService userService) {
        this.userService = userService;
    }

    public User getUser() {
        if (user == null) {
            Authentication authentication = SecurityContextHolder.getContext()
                                                                 .getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            log.info("Querying for authorized user [name: {}]", userDetails.getUsername());
            user = userService.findByUsername(userDetails.getUsername());
        }
        return user;
    }
}