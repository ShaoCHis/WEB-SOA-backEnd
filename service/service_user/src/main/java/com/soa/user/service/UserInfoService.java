package com.soa.user.service;

import com.soa.user.model.User;
import com.soa.user.repository.UserInfoRepository;
import com.soa.user.views.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public boolean updateUserInfo(UserVo userVo) {
        User byId = getById(userVo.getUserId());
        if(byId==null)
            //用户不存在
            return false;
        if(userVo.getEmail()!=null)
            byId.setEmail(userVo.getEmail());
        if(userVo.getName()!=null)
            byId.setName(userVo.getName());
        if(userVo.getOpenId()!=null)
            byId.setOpenId(userVo.getOpenId());
        if(userVo.getPhoneNumber()!=null)
            byId.setPhoneNumber(userVo.getPhoneNumber());
        userInfoRepository.save(byId);
        return true;
    }
}
