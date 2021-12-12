package com.soa.hospital.nettyServer;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: ShaoCHi
 * @Date: 2021/12/12 19:57
 * 管理所有webSocket连接
 */
public class ChatChannelHandlerPool {
    public ChatChannelHandlerPool(){}

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
