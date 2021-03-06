package com.soa.order.service;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.soa.order.model.Orders;
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
            ordersService.saveOrderInfo(reservation, 0);//0代表type为微信支付

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

    //付款时的支付状态，三秒查一次
    public Map<String, String> queryPayStatus(String reservationId) {
        try {
            Reservation reservation = reservationService.getReservationById(reservationId);
            if (reservation == null)
                return null;
            //封装提交参数
            Map paramMap = new HashMap();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
            paramMap.put("out_trade_no", reservationId);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());

            //设置请求内容
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap,ConstantPropertiesUtils.PARTNERKEY));
            client.setHttps(true);
            client.post();

            //得到微信接口返回数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
//            System.out.println("支付状态resultMap:"+resultMap);
            return resultMap;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //微信退款
    public Boolean refund(String id) {
        try {
            Orders orders = ordersService.getOrdersById(id);
            //修改state信息为退款中
            ordersService.refundOrders(id,2);
            //判断当前订单数据是否已经退款
            if(orders.getState() == 3)
                return true;//3代表已退款

            //微信退款
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("appid",ConstantPropertiesUtils.APPID);       //公众账号ID
            paramMap.put("mch_id",ConstantPropertiesUtils.PARTNER);   //商户编号
            paramMap.put("nonce_str",WXPayUtil.generateNonceStr());
            paramMap.put("transaction_id",orders.getTransactionID()); //微信订单号
            paramMap.put("out_trade_no",orders.getID()); //商户订单编号
            paramMap.put("out_refund_no","tk"+orders.getID()); //商户退款单号
//       paramMap.put("total_fee",paymentInfoQuery.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
//       paramMap.put("refund_fee",paymentInfoQuery.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap.put("total_fee","1");
            paramMap.put("refund_fee","1");
            String paramXml = WXPayUtil.generateSignedXml(paramMap,ConstantPropertiesUtils.PARTNERKEY);
            //设置调用接口内容
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/secapi/pay/refund");
            client.setXmlParam(paramXml);
            client.setHttps(true);
            //设置证书信息
            client.setCert(true);
            client.setCertPassword(ConstantPropertiesUtils.PARTNER);
            client.post();

            //接收返回数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            if (null != resultMap && WXPayConstants.SUCCESS.equalsIgnoreCase(resultMap.get("result_code"))) {
                //修改state信息为退款成功
                ordersService.refundOrders(id,3);
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
