package com.soa.user.service;

import com.soa.user.model.User;
import com.soa.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-18 08:44:15
 */
@Service
public class UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;

    public User getById(String id) {
        Optional<User> userById = userInfoRepository.findById(id);
        return userById.orElse(null);
    }

    
}
