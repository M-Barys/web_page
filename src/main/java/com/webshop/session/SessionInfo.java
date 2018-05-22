package com.webshop.session;

import com.webshop.model.view.SessionInfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Date;

@Component
//@RequestScope
@SessionScope
public class SessionInfo {

    @Autowired
    HttpSession httpSession;

    private final Date startTime;

    private boolean loggedIn = false;

    public SessionInfo() {
        startTime = Date.from(Instant.now());
    }

    public Date getStartTime() {
        return startTime;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public SessionInfoView getInfoView() {
        return SessionInfoView.builder()
                .startTime(startTime)
                .loggedIn(loggedIn)
                .sessionCookie(httpSession.getId())
                .build();
    }


}
