package com.example.hello.server.handler;

import com.example.hello.protocol.PacketCodeC;
import com.example.hello.protocol.request.MessageRequestPacket;
import com.example.hello.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        System.out.println(new Date() + ": 收到客户端消息：" + messageRequestPacket.getMessage());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
        System.out.println("处理普通消息的返回结果响应：" + messageResponsePacket);

        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
    }
}
