package com.cmpe451.retro.core;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SessionUtil {
    public long getUserId(HttpServletRequest httpServletRequest) {
        return (long) httpServletRequest.getSession().getAttribute(Constants.USER_ID_SESSION_ATTRIBUTE);
    }

    public String getRandomCode(int n){
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String randomCode = "";
        for(int i=0; i<n; i++){
            randomCode += possibleChars.charAt(Math.floor(Math.random() * possibleChars.length()));
        }
        System.out.println(randomCode);
        return randomCode;
    }
}