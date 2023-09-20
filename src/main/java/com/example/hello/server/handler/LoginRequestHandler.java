package com.example.hello.server.handler;

import com.example.hello.protocol.PacketCodeC;
import com.example.hello.protocol.request.LoginRequestPacket;
import com.example.hello.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginResponsePacket.getVersion());
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
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

}
