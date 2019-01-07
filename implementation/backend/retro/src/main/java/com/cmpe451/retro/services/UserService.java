package com.cmpe451.retro.services;


import com.cmpe451.retro.models.UpdateUserInfoRequestBody;
import com.cmpe451.retro.models.UserResponseModel;

import java.util.List;

public interface UserService {

    UserResponseModel getCurrentUser();

    UserResponseModel getUserById(long id);

    List<UserResponseModel> getAllUsers();

    void updateUserInfo(long userId, UpdateUserInfoRequestBody updateUserInfoBody);

}
