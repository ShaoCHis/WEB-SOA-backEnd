package com.soa.order.service;

import com.github.wxpay.sdk.WXPayUtil;
import com.soa.order.model.Reservation;
import com.soa.order.utils.ConstantPropertiesUtils;
import com.soa.order.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-11 12:48:11
 */
@Service
public class WeixinService {

    @Autowired
    ReservationService reservationService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    RedisTemplate redisTemplate;

    //生成微信支付二维码
    public Map createNative(String reservationId) {
        try {
            //从redis获取数据
            Map payMap = (Map)redisTemplate.opsForValue().get(reservationId);
            if(payMap != null) {
                return payMap;
            }
            Reservation reservation = reservationService.getReservationById(reservationId);
            if (reservation == null)
                return null;
            ordersService.saveOrderInfo(reservation, 1);//1代表type为微信支付

            Map paramMap = new HashMap();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            String body = reservation.getReserveDate() + "就诊" + reservation.getDepartmentName();
            paramMap.put("body", body);
            paramMap.put("out_trade_no", reservationId);
            //paramMap.put("total_fee", order.getAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap.put("total_fee", "1"); //0.01元
            paramMap.put("spbill_create_ip", "127.0.0.1");
            paramMap.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            paramMap.put("trade_type", "NATIVE");
            //调用微信生成二维码接口,httpclient调用
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置map参数
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
            client.setHttps(true);
            client.post();
            //返回相关数据
            String xml = client.getContent();
            //转换map集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            System.out.println("resultMap:" + resultMap);
            //封装返回结果集
            Map map = new HashMap<>();
            map.put("orderId", reservationId);
            map.put("totalFee", reservation.getCost());
            map.put("resultCode", resultMap.get("result_code"));
            map.put("codeUrl", resultMap.get("code_url")); //二维码地址

            if(resultMap.get("result_code") != null)
                redisTemplate.opsForValue().set(reservationId,map,120, TimeUnit.MINUTES);
            return map;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> queryPayStatus(String reservationId) {
        return null;
    }
}
