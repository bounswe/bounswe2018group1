package com.cmpe451.retro.controllers;


import com.cmpe451.retro.core.Constants;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.models.UpdateUserInfoRequestBody;
import com.cmpe451.retro.services.AuthenticationService;
import com.cmpe451.retro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getCurrentUser() { return userService.getCurrentUser(); }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") long id) { return userService.getUserById(id); }

    //TODO: decide pathvariable or requestparam
    //@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    //public User getUserById(@RequestParam("id") long id) { return authenticationService.getUserById(id); }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    @RequestMapping(value = "/user/info", method = RequestMethod.PUT)
    void updateInfo(@RequestBody UpdateUserInfoRequestBody updateUserInfoBody) {
        long userId = (long)httpServletRequest.getSession().getAttribute(Constants.USER_ID_SESSION_ATTRIBUTE);
        userService.updateUserInfo(userId, updateUserInfoBody);
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
