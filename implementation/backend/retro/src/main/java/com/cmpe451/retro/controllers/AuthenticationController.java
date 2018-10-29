package com.cmpe451.retro.controllers;

import com.cmpe451.retro.core.Constants;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.models.LoginRequestBody;
import com.cmpe451.retro.models.RegisterRequestBody;
import com.cmpe451.retro.models.UpdateUserInfoRequestBody;
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
    public long loginAttempt(@RequestBody LoginRequestBody loginRequest) {
        long userId = authenticationService.login(loginRequest);
        httpServletRequest.getSession().setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, userId);
        return userId;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestBody RegisterRequestBody registerRequestBody) {
        User user = authenticationService.register(registerRequestBody);
        //httpServletRequest.getSession().setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, userId);
        return user;

    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST, params = "randomCode")
    public long activate(@RequestParam("randomCode") String randomCode, String email) {
        long userId = authenticationService.activate(email, randomCode);
        return userId;
    }




}
