package com.cmpe451.retro.services;



import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.data.repositories.UserRepository;
import com.cmpe451.retro.models.LoginRequestBody;
import com.cmpe451.retro.models.RegisterRequestBody;
import com.cmpe451.retro.models.RetroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

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

        //TODO:HASHING
        if (user == null || !user.getPassword().equals(loginRequestBody.getPassword())) {
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
        User user = new User();
        user.setPassword(registerRequestBody.getPassword());
        user.setEmail(registerRequestBody.getEmail());
        user.setNickname(registerRequestBody.getNickname());
        user.setFirstName(registerRequestBody.getFirstName());
        user.setLastName(registerRequestBody.getLastName());

        Date now = new Date();
        user.setDateOfCreation(now);
        user.setDateOfUpdate(now);



        return user;
    }

}