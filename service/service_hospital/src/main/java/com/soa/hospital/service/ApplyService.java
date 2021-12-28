package com.soa.hospital.service;

import com.soa.hospital.model.Apply;
import com.soa.hospital.repository.ApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        Iterable<Apply> all = applyRepository.findAll();
        List<Apply> answer = new ArrayList<>();
        for(Apply tmp:all){
            answer.add(tmp);
        }
        return answer;
    }
}
