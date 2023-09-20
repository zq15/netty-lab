package com.example.hello.server;

import com.example.hello.protocol.Packet;
import com.example.hello.protocol.PacketCodeC;
import com.example.hello.protocol.request.LoginRequestPacket;
import com.example.hello.protocol.request.MessageRequestPacket;
import com.example.hello.protocol.response.LoginResponsePacket;
import com.example.hello.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requeatByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requeatByteBuf);
        System.out.println("开始处理请求: " + packet);

        // 判断是否是登录数据请求包
        if (packet instanceof LoginRequestPacket){
            // 登录流程
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            // 登录校验
            if (valid(loginRequestPacket)) {
                System.out.println(new Date() + "登录校验成功");
                loginResponsePacket.setSuccess(true);
            } else {
                // 校验失败
                System.out.println("校验失败");
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
            }
            System.out.println("处理登录消息的返回结果响应：" + loginResponsePacket);
            // 登录响应
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            // 处理消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ": 收到客户端消息：" + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            System.out.println("处理普通消息的返回结果响应：" + messageResponsePacket);

            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
