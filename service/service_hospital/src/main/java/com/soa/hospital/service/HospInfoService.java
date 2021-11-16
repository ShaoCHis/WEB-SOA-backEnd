package com.soa.hospital.service;

import com.soa.hospital.model.Hospital;
import com.soa.hospital.repository.HospInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
