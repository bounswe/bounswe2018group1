package com.cmpe451.retro.services;


import com.cmpe451.retro.core.SessionUtil;
import com.cmpe451.retro.data.entities.User;
import com.cmpe451.retro.data.repositories.UserRepository;
import com.cmpe451.retro.models.LoginRequestBody;
import com.cmpe451.retro.models.RegisterRequestBody;
import com.cmpe451.retro.models.RetroException;
import com.cmpe451.retro.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


@Service
@Transactional
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private StringUtil stringUtil = new StringUtil();

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

        /*
        if(!user.getActivated()) {
            throw new RetroException("Please activate your account to log in.", HttpStatus.UNAUTHORIZED);
        }
        */

        return user.getId();
    }


    @Override
    public long register(RegisterRequestBody registerRequestBody) {
        User user = createUser(registerRequestBody);
        entityManager.persist(user);
        entityManager.flush();
        return user.getId();
    }

    public User createUser(RegisterRequestBody registerRequestBody) {
        Optional<User> userOptionalEmail = Optional.ofNullable(userRepository.findByEmail(registerRequestBody.getEmail()));
        Optional<User> userOptionalNickname = Optional.ofNullable(userRepository.findByNickname(registerRequestBody.getNickname()));
        if(userOptionalEmail.isPresent()){
            throw new RetroException("Account with this email exists", HttpStatus.CONFLICT);
        }else if(userOptionalNickname.isPresent()){
            throw new RetroException("Account with this nickname exists", HttpStatus.CONFLICT);
        }

        if(!stringUtil.isValidPassword(registerRequestBody.getPassword()))
            throw new RetroException("Invalid password",HttpStatus.BAD_REQUEST);

        User user = new User();
        user.setPassword(passwordEncoder.encode(registerRequestBody.getPassword()));
        user.setEmail(registerRequestBody.getEmail());
        user.setNickname(registerRequestBody.getNickname());
        user.setFirstName(registerRequestBody.getFirstName());
        user.setLastName(registerRequestBody.getLastName());
        user.setMemoryList(new ArrayList<>());
        user.setActivated(false); //initially it is not activated
        user.setRandomCode(SessionUtil.getRandomCode(15));
        user.setListOfLocations(new ArrayList<>());
        user.setGender(User.Gender.NOT_TO_DISCLOSE);
        user.setBirthday(null);
        user.setBio("");
        Date now = new Date();
        user.setDateOfCreation(now);
        user.setDateOfUpdate(now);

        return user;
    }

    public long activate(String email, String randomCode){
        Optional<User> userOptionalEmail = Optional.ofNullable(userRepository.findByEmail(email));
        if(userOptionalEmail.isPresent()){
            User user = userOptionalEmail.get();
            if(user.getRandomCode().equals(randomCode)){
                user.setActivated(true);
            }
            userRepository.save(user); //update the user

            return user.getId();
        }

        throw new RetroException("Could not find the user with email: "+email, HttpStatus.EXPECTATION_FAILED);
    }

}