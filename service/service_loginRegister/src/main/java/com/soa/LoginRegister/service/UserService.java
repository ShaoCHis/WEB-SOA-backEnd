package com.soa.LoginRegister.service;

import com.soa.LoginRegister.model.*;
import com.soa.LoginRegister.repository.UserRepository;
import com.soa.utils.utils.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public User addNewUser (User user) {
        //Optional<User> opt=userRepository.findById(user.getUserId());
        //if(opt.isPresent()) return null;
        User optionUser=userRepository.findByEmail(user.getEmail());
        if(optionUser!=null) return null;
        double seed= ThreadLocalRandom.current().nextDouble();
        String uuid= UUID.randomUUID().toString().replaceAll("-","");
        user.setUserId(uuid);
        user.setSalt(HashHelper.computeSha256Hash(user.getUserId()+ seed));
        user.setPassword(HashHelper.computeSha256Hash(user.getPassword()+user.getSalt()));
        userRepository.save(user);
        return user;
    }
}
