package com.soa.order.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-27 20:54:54
 */
public class PostUtil {
    public static void postUrl(JSONObject postData,String url){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(postData, headers);
        System.out.println(client.postForEntity(url, requestEntity, JSONObject.class).getBody());
    }
}
