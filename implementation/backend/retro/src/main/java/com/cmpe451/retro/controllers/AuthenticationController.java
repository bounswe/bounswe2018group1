package com.cmpe451.retro.controllers;

import com.cmpe451.retro.core.Constants;
import com.cmpe451.retro.models.AuthenticationResponseModel;
import com.cmpe451.retro.models.LoginRequestBody;
import com.cmpe451.retro.models.RegisterRequestBody;
import com.cmpe451.retro.services.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ApiOperation(notes = "Either nickname or email is required.",
            value = "")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthenticationResponseModel loginAttempt(@RequestBody LoginRequestBody loginRequest) {
        long userId = authenticationService.login(loginRequest);
        httpServletRequest.getSession().setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, userId);
        String sessionID = httpServletRequest.getSession().getId();
        return new AuthenticationResponseModel(sessionID);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout() {
        httpServletRequest.getSession().setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, null);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public AuthenticationResponseModel register(@RequestBody RegisterRequestBody registerRequestBody) {
        long userId = authenticationService.register(registerRequestBody);
        httpServletRequest.getSession().setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, userId);
        String sessionID = httpServletRequest.getSession().getId();
        return new AuthenticationResponseModel(sessionID);

    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST, params = "randomCode")
    public long activate(@RequestParam("randomCode") String randomCode, String email) {
        return authenticationService.activate(email, randomCode);
    }


    long getUserId(){
        return (long) httpServletRequest.getSession().getAttribute(Constants.USER_ID_SESSION_ATTRIBUTE);
    }


}
