package com.example.hello.protocol.response;

import com.example.hello.protocol.Packet;
import lombok.Data;

import static com.example.hello.protocol.command.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
