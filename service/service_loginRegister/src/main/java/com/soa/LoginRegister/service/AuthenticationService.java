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

    @Autowired
    private HospitalRepository hospitalDAO;

    public User getUser(String sessionId) {
        if(sessionId == null)
            return null;
        String id=template.opsForValue().get(sessionId);
        if(id == null)
            return null;
        Optional<User> optionalUser= userDAO.findById(id);
        return optionalUser.orElse(null);
    }

    public Administrator getAdministrator(String sessionId) {
        if(sessionId == null)
            return null;
        String id=template.opsForValue().get(sessionId);
        if(id == null)
            return null;
        Optional<Administrator> optionalAdministrator= administratorDAO.findById(id);
        return optionalAdministrator.orElse(null);
    }

    public Hospital getHospital(String sessionId) {
        if(sessionId == null)
            return null;
        String id=template.opsForValue().get(sessionId);
        if(id == null)
            return null;
        Optional<Hospital> optional= hospitalDAO.findById(id);
        return optional.orElse(null);
    }

    public String createSessionId(String email,String password){
        User user=userDAO.findByEmail(email);
        if(user==null)
            return null;
        String passwordHashed= HashHelper.computeSha256Hash(password+user.getSalt());
        if(!user.getPassword().equals(passwordHashed))
            return null;

        double seed= ThreadLocalRandom.current().nextDouble();
        String sessionId=HashHelper.computeMD5Hash(user.getUserId()+ seed);
        template.opsForValue().set(sessionId, user.getUserId(),3 , TimeUnit.DAYS);

        return sessionId;
    }

    public String createAdminSession(String id,String password){
        Optional<Administrator> administrator=administratorDAO.findById(id);
        Administrator admin=administrator.orElse(null);
        if(admin==null)
            return null;
//        String hashedPassword= HashHelper.computeSha256Hash(password+admin.getSalt());
//        if(!admin.getPassword().equals(hashedPassword))
//            return null;
        if(!admin.getPassword().equals(password))
            return null;

        double seed= ThreadLocalRandom.current().nextDouble();
        String sessionId=HashHelper.computeMD5Hash(admin.getId()+ seed);
        template.opsForValue().set(sessionId, admin.getId(),3 , TimeUnit.DAYS);

        return sessionId;
    }

    public String createHospSession(String id, String password) {
        Optional<Hospital> hosp=hospitalDAO.findById(id);
        Hospital hospital=hosp.orElse(null);
        if(hospital==null)
            return null;
        if(!hospital.getPassword().equals(password))
            return null;

        double seed= ThreadLocalRandom.current().nextDouble();
        String sessionId=HashHelper.computeMD5Hash(hospital.getId()+ seed);
        template.opsForValue().set(sessionId, hospital.getId(),3 , TimeUnit.DAYS);

        return sessionId;
    }


    public void invalidateSessionId(String sessionId){
        template.delete(sessionId);
    }
}
