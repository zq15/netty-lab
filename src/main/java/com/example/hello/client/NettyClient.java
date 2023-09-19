package com.example.hello.client;

import com.example.hello.client.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1. 指定线程模型
                .group(workerGroup)
                // 2. 指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3. IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new ClientHandler());
                    }
                });
        connect(bootstrap, "localhost", 8001, MAX_RETRY);
    }

    // 使用指数退退避算法
    // 隔 1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接，然后到达一定次数之后就放弃连接
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        // 4. 建立连接
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()){
                System.out.println("连接成功");
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
}
