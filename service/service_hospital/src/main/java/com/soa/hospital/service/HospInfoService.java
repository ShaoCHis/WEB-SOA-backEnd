package com.soa.hospital.service;

import com.soa.hospital.model.Hospital;
import com.soa.hospital.repository.HospInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @ program: demo
 * @ description: HospInfoService
 * @ author: ShenBo
 * @ date: 2021-11-16 18:33:35
 */
@Service
public class HospInfoService {
    @Autowired
    HospInfoRepository hospInfoRepository;

    public Hospital getById(String id) {
        Optional<Hospital> hospById = hospInfoRepository.findById(id);
        return hospById.orElse(null);
    }

    @Transactional
    public void update(Hospital hospital) {
        Hospital hosp = hospInfoRepository.save(hospital);
    }

    @Transactional
    public void updateNoticeById(Hospital hospital){
        Optional<Hospital> byId = hospInfoRepository.findById(hospital.getId());
        Hospital hospital1=byId.orElse(null);
        hospital1.setNotice(hospital.getNotice());
        Hospital hosp = hospInfoRepository.save(hospital1);
    }

    @Transactional
    public void updateLogoById(Hospital hospital) {
        Optional<Hospital> byId = hospInfoRepository.findById(hospital.getId());
        Hospital hospital1=byId.orElse(null);
        hospital1.setImage(hospital.getImage());
        Hospital hosp = hospInfoRepository.save(hospital1);
    }
}
