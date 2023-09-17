package com.example.netty.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.example.netty.c1.ByteBufferUtil.debugAll;

//  StandardCharsets encode decode 加解码
public class Translate2 {

    public static void main(String[] args) {
        // 准备两个字符串
        String str1 = "hello";
        String str2 = "";

        // StandardCharsets 的 encode 方法获得 bytebuffer
        // 此时的 ByteBuffer 就是 读模式
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(str1);
        debugAll(buffer);


        str2 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(str2);
        debugAll(buffer);

    }
}
