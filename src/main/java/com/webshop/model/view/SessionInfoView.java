package com.webshop.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionInfoView {
    private Date startTime;
    private String sessionCookie;
    private boolean loggedIn;
}
