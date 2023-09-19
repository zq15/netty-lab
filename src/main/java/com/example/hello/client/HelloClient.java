package com.example.hello.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        new Bootstrap()
                .group(new NioEventLoopGroup())  // 1.
                .channel(NioSocketChannel.class)  //2，选择客户 socket 实现类 NioSocketChannel 表示基于 netty 实现
                .handler(new ChannelInitializer<Channel>() { // 3.
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1", 8080)
                .sync()
                .channel()
                .writeAndFlush(new Date() + ": hello world");

    }
}
