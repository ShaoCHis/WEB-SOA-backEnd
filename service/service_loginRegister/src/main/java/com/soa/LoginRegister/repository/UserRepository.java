package com.soa.LoginRegister.repository;

import com.soa.LoginRegister.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User,String> {
    default User findByEmail(String email){
        Iterable<User> users=findAll();
        for(User user:users){
            if(Objects.equals(user.getEmail(), email)){
                return user;
            }
        }
        return null;
    }
}
