package com.example.hello.protocol.request;

import com.example.hello.protocol.Packet;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return null;
    }
}
