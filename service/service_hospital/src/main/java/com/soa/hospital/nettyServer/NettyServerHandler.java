package com.soa.hospital.nettyServer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soa.hospital.service.HospitalService;
import com.soa.hospital.views.HospitalInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final Properties properties = new Properties();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启");
        //添加到通道组
        ChatChannelHandlerPool.channelGroup.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //服务端接收客户端发送的消息
        ByteBuf in = (ByteBuf) msg;
        String information=in.toString(CharsetUtil.UTF_8);
        System.out.println("服务端接收到数据:" + information);

        if(Objects.equals(information, "UpdateInformation")){
            //用RestTemplate GET对后端其他项目进行接口调用，进行json和Object类型之间的转化
            RestTemplate restTemplate = new RestTemplate();
            String location = "http://127.0.0.1:18080/api/hospitals/updateInfo/1";
            JSONObject json = restTemplate.getForEntity(location, JSONObject.class).getBody().getJSONObject("data");
            System.out.println(json);
            System.out.println(String.valueOf(json));
            HospitalInfo hospitalInfo= JSON.parseObject(String.valueOf(json),HospitalInfo.class);


            String newLocation="http://127.0.0.1:8083/hospital/hospitals/update";
            //请求参数JOSN类型
            RestTemplate client = new RestTemplate();
            System.out.println(client.postForEntity(newLocation, json, JSONObject.class).getBody());
            //服务端给客户端发送消息
            ctx.writeAndFlush(Unpooled.copiedBuffer("服务端数据更新成功!", CharsetUtil.UTF_8));
        }

//        RequestBody body = new FormBody.Builder()
//                .add("键", "值")
//                .add("键", "值").build();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端连接断开，通道关闭");
        ChatChannelHandlerPool.channelGroup.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public Response okHttpGet(String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        return getResponse(okHttpClient, request);
    }

    private Response getResponse(OkHttpClient okHttpClient, Request request) {
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response okHttpPost(String url, RequestBody body){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return getResponse(okHttpClient, request);
    }
}
