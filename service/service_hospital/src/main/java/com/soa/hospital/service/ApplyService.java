package com.soa.hospital.service;

import com.soa.hospital.model.Apply;
import com.soa.hospital.repository.ApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        applyRepository.save(apply);
    }
}