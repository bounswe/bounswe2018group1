package com.cmpe451.retro.services;


import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.models.LoginRequestBody;
import com.cmpe451.retro.models.RegisterRequestBody;

import java.util.List;

public interface AuthenticationService {

    long login(LoginRequestBody loginRequestBody);

    long register(RegisterRequestBody registerRequestBody);

    User getUser();

    List<User> getAllUsers();

}
