package com.example.hello.server;

import com.example.hello.codec.PacketDecoder;
import com.example.hello.codec.PacketEncoder;
import com.example.hello.codec.Spliter;
import com.example.hello.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    private static final int PORT = 8001;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 创建启动引导类
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        // 登录请求处理器
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());
                        // 单聊消息请求处理器
                        ch.pipeline().addLast(new MessageRequestHandler());
                        // 创建群请求处理器
                        ch.pipeline().addLast(new CreateGroupRequestHandler());
                        // 加群请求处理器
                        ch.pipeline().addLast(new JoinGroupRequestHandler());
                        // 退群请求处理器
                        ch.pipeline().addLast(new QuitGroupRequestHandler());
                        // 获取群成员请求处理器
                        ch.pipeline().addLast(new ListGroupMembersRequestHandler());
                        // 登出请求处理器
                        ch.pipeline().addLast(new LogoutRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        bind(serverBootstrap, PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口绑定成功");
                } else {
                    System.err.println("端口绑定失败");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }

}
