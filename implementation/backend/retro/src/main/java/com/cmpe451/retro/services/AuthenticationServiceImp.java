package com.cmpe451.retro.services;


import com.cmpe451.retro.core.Constants;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.data.repositories.UserRepository;
import com.cmpe451.retro.models.LoginRequestBody;
import com.cmpe451.retro.models.RegisterRequestBody;
import com.cmpe451.retro.models.RetroException;
import com.cmpe451.retro.models.UpdateUserInfoRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public long login(LoginRequestBody loginRequestBody) {

        User user = null;
        if (loginRequestBody.getEmail() != null && !loginRequestBody.getEmail().equals("")) {
            user = userRepository.findByEmail(loginRequestBody.getEmail());
        } else if (loginRequestBody.getNickname() != null && !loginRequestBody.getNickname().equals("")) {
            user = userRepository.findByNickname(loginRequestBody.getNickname());
        } else {
            throw new RetroException("You can not leave both email and nickname empty.", HttpStatus.BAD_REQUEST);
        }

        if (user == null || !passwordEncoder.matches(loginRequestBody.getPassword(),user.getPassword())) {
            throw new RetroException("Incorrect login information.", HttpStatus.UNAUTHORIZED);
        }

        return user.getId();
    }

    @Override
    public long register(RegisterRequestBody registerRequestBody) {

        User user = createUser(registerRequestBody);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    private User createUser(RegisterRequestBody registerRequestBody) {
        //check if a user with this email / nickname exists //TODO:check
        Optional<User> userOptionalEmail = Optional.ofNullable(userRepository.findByEmail(registerRequestBody.getEmail()));
        Optional<User> userOptionalNickname = Optional.ofNullable(userRepository.findByNickname(registerRequestBody.getNickname()));
        if(userOptionalEmail.isPresent()){
            throw new RetroException("Account with this email exists", HttpStatus.CONFLICT);
        }else if(userOptionalNickname.isPresent()){
            throw new RetroException("Account with this nickname exists", HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(registerRequestBody.getPassword()));
        user.setEmail(registerRequestBody.getEmail());
        user.setNickname(registerRequestBody.getNickname());
        user.setFirstName(registerRequestBody.getFirstName());
        user.setLastName(registerRequestBody.getLastName());
        user.setMemoryList(new ArrayList<>());

        Date now = new Date();
        user.setDateOfCreation(now);
        user.setDateOfUpdate(now);

        return user;
    }


    @Override
    public User getCurrentUser() {

        long userID = (long)httpServletRequest.getSession().getAttribute(Constants.USER_ID_SESSION_ATTRIBUTE);
        Optional<User> user = userRepository.findById(userID);

        if(user.isPresent())
            return user.get();

        throw new RetroException("You have tried to access an authorised page. Please login and try again.",HttpStatus.UNAUTHORIZED);
    }

    @Override
    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent())
            return user.get();

        throw new RetroException("You have tried to access an authorised page. Please login and try again.",HttpStatus.UNAUTHORIZED);
    }

    @Override
    public List<User> getAllUsers() {

        List<User> allUsers = userRepository.findAll();

        return allUsers;

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