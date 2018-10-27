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
    public void loginAttempt(@RequestBody LoginRequestBody loginRequest) {
        long userId = authenticationService.login(loginRequest);
        httpServletRequest.getSession().setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, userId);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public long register(@RequestBody RegisterRequestBody registerRequestBody) {
        long userId = authenticationService.register(registerRequestBody);
        httpServletRequest.getSession().setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, userId);
        return userId;

    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getCurrentUser() { return authenticationService.getCurrentUser(); }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") long id) { return authenticationService.getUserById(id); }

    //TODO: decide pathvariable or requestparam
    //@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    //public User getUserById(@RequestParam("id") long id) { return authenticationService.getUserById(id); }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> getAllUsers() { return authenticationService.getAllUsers(); }

    @RequestMapping(value = "/user/info", method = RequestMethod.PUT)
    void updateInfo(@RequestBody UpdateUserInfoRequestBody updateUserInfoBody) {
        long userId = (long)httpServletRequest.getSession().getAttribute(Constants.USER_ID_SESSION_ATTRIBUTE);
        authenticationService.updateUserInfo(userId, updateUserInfoBody);
    }

//    @RequestMapping(value = "/info", method = RequestMethod.GET)
//    public UserInfoResponse getInfo(String nickname) {
//        if (nickname == null) {
//            long userId = sessionUtil.getUserId(httpServletRequest);
//            return userService.getInfo(userId);
//        }
//
//        return userService.getInfo(nickname);
//
//    }

}
