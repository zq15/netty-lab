package com.example.hello.protocol.request;

import com.example.hello.protocol.Packet;
import lombok.Data;

import static com.example.hello.protocol.command.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return LOGOUT_REQUEST;
    }
}
