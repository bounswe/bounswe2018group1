package com.cmpe451.retro.core;

import com.cmpe451.retro.models.RetroException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        if(httpServletRequest.getMethod().equals("OPTIONS") || httpServletRequest.getMethod().equals("GET")
                || httpServletRequest.getContentType().equals("multipart/form-data")) {
            return true;
        }
        HttpSession session = httpServletRequest.getSession();
        session.setMaxInactiveInterval(60*60*24*360);
        boolean isActivate = (httpServletRequest.getRequestURI()).contains("activate");
        if(!isActivate){
            if (session.isNew() || session.getAttribute(Constants.USER_ID_SESSION_ATTRIBUTE) == null ) {
                throw new RetroException("Your session has timed out. Please login again.", HttpStatus.FORBIDDEN);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}