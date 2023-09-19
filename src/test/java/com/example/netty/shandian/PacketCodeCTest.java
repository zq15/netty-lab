//package com.example.netty.shandian;
//
//import com.example.hello.protocol.request.LoginRequestPacket;
//import com.example.hello.protocol.Packet;
//import com.example.hello.protocol.PacketCodeC;
//import com.example.hello.serialize.Serializer;
//import com.example.hello.serialize.impl.JSONSerializer;
//import io.netty.buffer.ByteBuf;
//import org.junit.Assert;
//import org.junit.Test;
//
//public class PacketCodeCTest {
//
//    @Test
//    public void encode(){
//        Serializer serializer = new JSONSerializer();
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//
//        loginRequestPacket.setVersion((byte) 1);
//        loginRequestPacket.setUserId(123);
//        loginRequestPacket.setUsername("zhangsan");
//        loginRequestPacket.setPassword("password");
//
//        PacketCodeC packetCodeC = new PacketCodeC();
//        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
//        Packet decodePacket = packetCodeC.decode(byteBuf);
//
//        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodePacket));
//
//    }
//}
