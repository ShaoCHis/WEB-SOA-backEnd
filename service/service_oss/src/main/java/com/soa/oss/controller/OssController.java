package com.soa.oss.controller;

import com.soa.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ program: demo
 * @ description: OssController
 * @ author: ShenBo
 * @ date: 2021-11-16 08:46:12
 */
@RestController
@RequestMapping("/oss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    OssService ossService;


}
