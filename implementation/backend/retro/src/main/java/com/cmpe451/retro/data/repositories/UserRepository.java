package com.cmpe451.retro.data.repositories;


import com.cmpe451.retro.data.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByNickname(String nickName);

    User findByEmail(String email);

    List<User> findAll();
}
