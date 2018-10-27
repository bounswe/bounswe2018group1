package com.cmpe451.retro.controllers;

import com.cmpe451.retro.core.Constants;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.models.LoginRequestBody;
import com.cmpe451.retro.models.RegisterRequestBody;
import com.cmpe451.retro.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void loginAttempt(@RequestBody LoginRequestBody loginRequest) {
        long userId = authenticationService.login(loginRequest);
        httpServletRequest.getSession().setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, userId);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterRequestBody registerRequestBody) {
        long userId = authenticationService.register(registerRequestBody);
        httpServletRequest.getSession().setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, userId);

    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser() { return authenticationService.getUser(); }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> getAllUsers() { return authenticationService.getAllUsers(); }


}
