package com.example.netty.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.example.netty.c1.ByteBufferUtil.debugRead;

// 非阻塞模式
@Slf4j
public class Server2 {
    public static void main(String[] args) {
        // 使用 nio 来理解阻塞模式，单线程
        // 0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 1.创建了服务器
        try (ServerSocketChannel ssc = ServerSocketChannel.open();) {
            // 2.绑定监听端口
            ssc.bind(new InetSocketAddress(8080));

            // 3. 连接集合
            List<SocketChannel> channels = new ArrayList<>();
            while (true) {
                // 设置成非阻塞模式没有连接时返回 null，不会阻塞线程
                ssc.configureBlocking(false);

                SocketChannel sc = ssc.accept();
                // 通道不为空时才将连接放入到集合中
                if (sc != null) {
                    System.out.println("after connecting...");
                    channels.add(sc);
                }
                for (SocketChannel channel : channels) {
                    channel.configureBlocking(false);

                    int read = channel.read(buffer); // 阻塞方法，线程停止运行
                    if (read > 0) {
                        buffer.flip();
                        debugRead(buffer);
                        buffer.clear();
                        System.out.println("after reading");
                    }

                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
