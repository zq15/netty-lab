package com.example.hello.client;

import com.example.hello.client.console.ConsoleCommandManager;
import com.example.hello.client.console.LoginConsoleCommand;
import com.example.hello.client.handler.*;
import com.example.hello.codec.PacketDecoder;
import com.example.hello.codec.PacketEncoder;
import com.example.hello.codec.Spliter;
import com.example.hello.handler.IMIdleStateHandler;
import com.example.hello.protocol.request.LoginRequestPacket;
import com.example.hello.protocol.request.MessageRequestPacket;
import com.example.hello.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8001;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1. 指定线程模型
                .group(workerGroup)
                // 2. 指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 3. IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());

                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        // 登录响应处理器
                        ch.pipeline().addLast(new LoginResponseHandler());
                        // 收消息处理器
                        ch.pipeline().addLast(new MessageResponseHandler());
                        // 创建群响应处理器
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        // 加群响应处理器
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        // 退群响应处理器
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        // 获取群成员响应处理器
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        // 群消息响应
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                        // 登出响应处理器
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());

                        // 心跳定时器
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    // 使用指数退退避算法
    // 隔 1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接，然后到达一定次数之后就放弃连接
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        // 4. 建立连接
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()){
                System.out.println("连接成功");
                Channel channel = ((ChannelFuture) future).channel();
                // 连接成功以后，启动控制台线程
                startConsoleThread(channel);
            } else if (retry == 0){
                System.out.println("失败的次数已用完，放弃连接!");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连.....");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    public static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()){
                if (!SessionUtil.hasLogin(channel)){
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }


}
