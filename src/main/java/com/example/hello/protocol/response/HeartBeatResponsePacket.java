package com.example.hello.protocol.response;

import com.example.hello.protocol.Packet;

import static com.example.hello.protocol.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
