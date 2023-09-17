package com.example.netty.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.example.netty.c1.ByteBufferUtil.debugAll;

// put str 的 getByte
public class Translate1 {

    public static void main(String[] args) {
        // 准备两个字符串
        String str1 = "hello";
        String str2 = "";

        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 通过字符串的getByte方法获得字节数组，放入缓冲区
        buffer.put(str1.getBytes());
        debugAll(buffer);

        // 将缓冲区的数组转化为字符串
        // 切换模式
//        buffer.flip();

        // 通过 StandardCharsets 解码，获取 CharBuffer，在通过toString获得字符串
        str2 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(str2);
        debugAll(buffer);
    }
}
