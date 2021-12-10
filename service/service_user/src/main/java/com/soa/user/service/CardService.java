package com.soa.user.service;

import com.soa.user.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-10 21:50:51
 */
@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

}
