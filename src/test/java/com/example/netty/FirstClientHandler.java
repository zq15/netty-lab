package com.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("FirstClientHandler channelActive");
        // 1. 获取数据
        ByteBuf buffer = getByteBuf(ctx);
        // 2. 写数据
        ctx.writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. 获取二进制抽象
        ByteBuf buffer = ctx.alloc().buffer();
        // 2. 准备数据，指定数据的字符集为 utf-8
        byte[] bytes = "你好，zds".getBytes(StandardCharsets.UTF_8);
        buffer.writeBytes(bytes);
        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端读取到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));
    }
}
