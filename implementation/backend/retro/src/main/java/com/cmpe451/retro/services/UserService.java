package com.cmpe451.retro.services;


import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.models.UpdateUserInfoRequestBody;

import java.util.List;

public interface UserService {

    User getCurrentUser();

    User getUserById(long id);

    List<User> getAllUsers();

    void updateUserInfo(long userId, UpdateUserInfoRequestBody updateUserInfoBody);

}
