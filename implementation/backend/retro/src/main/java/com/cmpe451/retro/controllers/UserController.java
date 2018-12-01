package com.cmpe451.retro.controllers;


import com.cmpe451.retro.core.Constants;
import com.cmpe451.retro.models.UpdateUserInfoRequestBody;
import com.cmpe451.retro.models.UserResponseModel;
import com.cmpe451.retro.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public UserResponseModel getCurrentUser() { return userService.getCurrentUser(); }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UserResponseModel getUserById(@PathVariable("id") long id) { return userService.getUserById(id); }

    //TODO: decide pathvariable or requestparam
    //@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    //public User getUserById(@RequestParam("id") long id) { return authenticationService.getUserById(id); }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<UserResponseModel> getAllUsers() { return userService.getAllUsers(); }

    @ApiOperation(notes = "Only fields that are sent is updated. No field is required.",
            value = "")
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
