package com.soa.hospital.service;

import com.soa.hospital.model.Apply;
import com.soa.hospital.repository.ApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-18 15:28:28
 */
@Service
public class ApplyService {
    @Autowired
    ApplyRepository applyRepository;

    @Transactional
    public void applyInfo(Apply apply) {
        Apply applyInfo=new Apply();
        applyInfo.setCode(apply.getCode());
        applyInfo.setContact(apply.getContact());
        applyInfo.setDescription(apply.getDescription());
        applyInfo.setImage(apply.getImage());
        applyInfo.setLevel(apply.getLevel());
        applyInfo.setName(apply.getName());
        applyInfo.setLocation(apply.getLocation());
        applyInfo.setStatus(apply.getStatus());
        applyRepository.save(applyInfo);
    }

    public List<Apply> getApplyList() {
        List<Apply> answer = applyRepository.queryAllList();
        return answer;
    }

    public Apply getApplyInfo(String code) {
        Optional<Apply> byId = applyRepository.findById(code);
        Apply apply = byId.orElse(null);
        return apply;
    }

    public void updateStatus(String code) {
        Apply applyInfo = getApplyInfo(code);
        applyInfo.setStatus(1);//加入系统
        applyRepository.save(applyInfo);
    }
}
