package com.webshop.controllers;

import com.webshop.model.SessionInfoView;
import com.webshop.session.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;


@RestController
@RequestMapping("/session")
public class SessionController {

    private final UUID adminRandomSessionToken = UUID.randomUUID();

    @Autowired
    SessionInfo sessionInfo;

    @RequestMapping(method = RequestMethod.GET)
    public SessionInfoView getSessionInfo() {
        return sessionInfo.getInfoView();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/token")
    public boolean displayAdminToken() {
        System.err.print("admin token:" + adminRandomSessionToken.toString());
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public SessionInfoView login(String token) {
        if (token.compareTo(adminRandomSessionToken.toString()) == 0) {
            sessionInfo.setLoggedIn(true);
        } else {
            throw new IllegalArgumentException("bad token");
        }
        return sessionInfo.getInfoView();
    }
}


