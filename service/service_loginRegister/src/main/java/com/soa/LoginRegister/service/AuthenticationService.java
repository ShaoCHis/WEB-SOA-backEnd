package com.soa.LoginRegister.service;

import com.soa.LoginRegister.model.*;
import com.soa.LoginRegister.repository.*;
import com.soa.utils.utils.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationService {
    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private UserRepository userDAO;

    @Autowired
    private AdministratorRepository administratorDAO;

    public User getUser(String sessionId) {

        if(sessionId == null) return null;
        String id=template.opsForValue().get(sessionId);
        if(id == null) {
            return null;
        }
        Optional<User> optionalUser= userDAO.findById(id);

        return optionalUser.orElse(null);
    }

    public Administrator getAdministrator(String sessionId) {

        if(sessionId == null) return null;
        String id=template.opsForValue().get(sessionId);
        if(id == null) {
            return null;
        }
        Optional<Administrator> optionalAdministrator= administratorDAO.findById(id);

        return optionalAdministrator.orElse(null);
    }


    public String createSessionId(String username,String password){
        Optional<User> optionalUser = userDAO.findById(username);
        if(!optionalUser.isPresent()){
            return null;
        }
        User user=optionalUser.get();
        String passwordHashed= HashHelper.computeSha256Hash(password+user.getSalt());
        if(!user.getPassword().equals(passwordHashed)){
            return null;
        }

        double seed= ThreadLocalRandom.current().nextDouble();
        String sessionId=HashHelper.computeMD5Hash(user.getUserId()+ seed);
        template.opsForValue().set(sessionId, user.getUserId(),3 , TimeUnit.DAYS);

        return sessionId;

    }

    public void invalidateSessionId(String sessionId){
        template.delete(sessionId);
    }

}
