package com.example.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

import static com.example.netty.c1.ByteBufferUtil.debugAll;

@Slf4j
public class ByteBufferReadWriteTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 写入一个字节
        buffer.put((byte) 97);
        debugAll(buffer);

        // 写入四个字节
        buffer.put(new byte[]{98, 99, 100, 101});
        debugAll(buffer);

        // 获取数据
        buffer.flip();
        debugAll(buffer);
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        debugAll(buffer);

        // 使用 compact
        buffer.compact();
        debugAll(buffer);

        // 再次写入
        buffer.put(new byte[]{102, 103});
        debugAll(buffer);
    }
}