package com.example.netty.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.example.netty.c1.ByteBufferUtil.debugAll;

//  StandardCharsets encode decode 加解码
public class Translate3 {

    public static void main(String[] args) {
        // 准备两个字符串
        String str1 = "hello";
        String str2 = "";

        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 通过StandardCharsets的encode方法获得ByteBuffer
        // 此时获得的ByteBuffer为读模式，无需通过flip切换模式
        ByteBuffer.wrap(str1.getBytes());
        buffer.put(str1.getBytes());
        debugAll(buffer);


        str2 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(str2);
        debugAll(buffer);

    }
}
