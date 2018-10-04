package com.cmpe451.retro.core;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SessionUtil {
    public long getUserId(HttpServletRequest httpServletRequest) {
        return (long) httpServletRequest.getSession().getAttribute("id");
    }
}