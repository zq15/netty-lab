package com.example.hello.server;

import com.example.hello.protocol.Packet;
import com.example.hello.protocol.PacketCodeC;
import com.example.hello.protocol.request.LoginRequestPacket;
import com.example.hello.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + "服务端开始处理登录校验");
        ByteBuf requeatByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.INSTANCE.decode(requeatByteBuf);
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
            // 登录响应
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
