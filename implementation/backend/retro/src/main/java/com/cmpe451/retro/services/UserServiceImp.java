package com.cmpe451.retro.services;

import com.cmpe451.retro.core.Constants;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.data.repositories.UserRepository;
import com.cmpe451.retro.models.RetroException;
import com.cmpe451.retro.models.UpdateUserInfoRequestBody;
import com.cmpe451.retro.models.UserResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public UserResponseModel getCurrentUser() {

        long userID = (long)httpServletRequest.getSession().getAttribute(Constants.USER_ID_SESSION_ATTRIBUTE);
        Optional<User> user = userRepository.findById(userID);

        if(user.isPresent())
            return new UserResponseModel(user.get());

        throw new RetroException("You have tried to access an authorised page. Please login and try again.", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public UserResponseModel getUserById(long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent())
            return new UserResponseModel(user.get());

        throw new RetroException("User not found.",HttpStatus.NOT_FOUND);
    }

    @Override
    public List<UserResponseModel> getAllUsers() {

        List<User> allUsers = userRepository.findAll();

        return allUsers.stream().map(UserResponseModel::new).collect(Collectors.toList());
    }

    @Override
    public void updateUserInfo(long userId, UpdateUserInfoRequestBody updateUserInfoBody) { //TODO:check
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if (updateUserInfoBody.getFirstName() != null && !updateUserInfoBody.getFirstName().equals("")) {
                user.setFirstName(updateUserInfoBody.getFirstName());
            }

            if (updateUserInfoBody.getLastName() != null && !updateUserInfoBody.getLastName().equals("")) {
                user.setLastName(updateUserInfoBody.getLastName());
            }

            if (updateUserInfoBody.getNewPassword() != null && updateUserInfoBody.getOldPassword() != null &&
                    !updateUserInfoBody.getNewPassword().equals("") && !updateUserInfoBody.getOldPassword().equals("")) {

                if (user.getPassword().equals(updateUserInfoBody.getOldPassword())) {
                    user.setPassword(updateUserInfoBody.getNewPassword());
                } else {
                    throw new RetroException("Old password is not correct", HttpStatus.BAD_REQUEST);
                }

            }
            userRepository.save(user);

        }else{
            throw new RetroException("Could not find the user", HttpStatus.EXPECTATION_FAILED);
        }

    }
}