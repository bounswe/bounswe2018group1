package com.cmpe451.retro.data.repositories;


import com.cmpe451.retro.data.entities.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User,Long>{

    User findByNickname(String nickName);

    User findByEmail(String email);

}
