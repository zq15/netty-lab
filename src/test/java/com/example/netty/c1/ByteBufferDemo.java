package com.example.netty.c1;

import java.nio.ByteBuffer;

import static com.example.netty.c1.ByteBufferUtil.debugAll;

// 粘包与半包问题解决
public class ByteBufferDemo {

    public static void main(String[] args) {
        /**
         * 网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔 但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
         *
         * Hello,world\n
         * I'm zhangsan\n
         * How are you?\n
         * 变成了下面的两个 byteBuffer (黏包，半包)
         *
         * Hello,world\nI'm zhangsan\nHo
         * w are you?\n
         * 现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分隔的数据
         */
        ByteBuffer buffer = ByteBuffer.allocate(32);
        // 模拟粘包+半包
        buffer.put("Hello,world\nI'm Nyima\nHo".getBytes());
        // 调用split函数处理
        split(buffer);
        buffer.put("w are you?\n".getBytes());
        split(buffer);
        buffer.flip();
        debugAll(buffer);
    }

    private static void split(ByteBuffer buffer) {
        // 切换成读模式
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {

            // 遍历寻找分隔符
            // get(i) 不会移动 position
            if (buffer.get(i) == '\n') {
                // 缓冲区长度
                int length = i + 1 - buffer.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 将前面的内容写入target中
                for (int j = 0; j < length; j++) {
                    // 将buffer中的数据写入 target 中
                    target.put(buffer.get());
                }
                // 打印查看结果
                debugAll(target);
            }
        }

        // 切换为写模式，但是缓冲区可能未读完，这里需要使用 compact
        buffer.compact();
    }
}
