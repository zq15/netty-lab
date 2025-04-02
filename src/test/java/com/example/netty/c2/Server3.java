package com.example.netty.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.example.netty.c1.ByteBufferUtil.debugAll;
import static com.example.netty.c1.ByteBufferUtil.debugRead;

// 非阻塞模式
@Slf4j
public class Server3 {
    public static void main(String[] args) {
        // 1.创建了服务器
        try (ServerSocketChannel channel = ServerSocketChannel.open();) {
            channel.bind(new InetSocketAddress(8089));
            System.out.println(channel);
            Selector selector = Selector.open();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int count = selector.select();
//                int count = selector.selectNow();
                log.debug("select count: {}", count);
//                if (count <= 0) {
//                    continue;
//                }
                // 获取所有事件
                Set<SelectionKey> keys = selector.selectedKeys();

                // 遍历所有事件，逐一处理
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    // 怕判断事件类型
                    if (key.isAcceptable()) {
                        ServerSocketChannel c = (ServerSocketChannel) key.channel();
                        SocketChannel sc = c.accept();
                        log.debug("{}", sc);
                    }else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(128);
                        int read = sc.read(buffer);
                        if (read==-1){
                            key.cancel();
                            sc.close();
                        }else {
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }
                    // 处理完毕，事件移除
                    iterator.remove();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
